package cn.poolify.core.timer;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @Author: HCJ
 * @DateTime: 2024/12/17
 * @Description:
 * TODO: 状态管理
 **/
public class HashedWheelTimer implements Timer {

    private static final int MAX_CAPACITY = 1<<30;
    // 最大一个时钟走200ms
    private static final long MAX_TICK_DURATION = TimeUnit.MICROSECONDS.toNanos(200);
    private static final long MAX_TIMEOUT_COUNT = 1<<30;


    // 工作队列 -- 监控的线程池队列
    private final Queue<HashedWheelTimeout> timeouts = new LinkedBlockingQueue<>();
    // 桶数组
    private final HashedWheelBucket[] wheel;
    // wheel是2的多少次方
    private final int wheelBit;
    // 掩码
    private final int mask;
    // 记录队列中有多少个任务
    private final AtomicLong pendingTimeoutsCount = new AtomicLong(0);
    // 队列中最多可以有多少任务
    private final long maxPendingTimeoutsCount;
    // 一次运行的持续时间
    private final long tickDuration;

    private volatile long startTime;



    public HashedWheelTimer(ThreadFactory threadFactory,
                            long tickDuration,
                            TimeUnit unit,
                            long maxPendingTimeoutsCount,
                            int initialCapacity
                            ){
        if (threadFactory == null) {
            throw new NullPointerException("threadFactory");
        }
        if (unit == null) {
            throw new NullPointerException("unit");
        }
        if(maxPendingTimeoutsCount<0){
            throw new IllegalStateException("maxPendingTimeoutsCount cannot be negative");
        }
        this.tickDuration = Math.min(MAX_TICK_DURATION,unit.toNanos(tickDuration));

        this.maxPendingTimeoutsCount = Math.min(MAX_TIMEOUT_COUNT,maxPendingTimeoutsCount);

        // wheel 相关初始化
        this.wheelBit = Integer.numberOfLeadingZeros(initialCapacity - 1)+1;
        int cap = 1<<this.wheelBit;
        if(cap>MAX_CAPACITY){
            throw new IllegalStateException("exceeding maximum capacity");
        }
        this.wheel = new HashedWheelBucket[cap];
        this.mask = cap-1;
        initialWheel();

        // 构造扫描线程
        TimerTickerRunnable tickerRunnable = new TimerTickerRunnable(this);
        Thread scanThread = threadFactory.newThread(tickerRunnable);
        // 扫描线程启动
        scanThread.start();
    }

    private void initialWheel() {
        for (int i = 0; i < wheel.length; i++) {
            wheel[i] = new HashedWheelBucket();
        }
    }


    private static class HashedWheelTimeout implements Timeout {
        // 定义状态
        private static final Integer HWT_INIT = 1;
        private static final Integer HWT_CANCEL = 2;
        private static final Integer HWT_EXPIRE = 3;
        private final HashedWheelTimer timer;

        private final AtomicInteger state = new AtomicInteger(HWT_INIT);
        // 任务
        private final TimeTask task;
        // 期限
        private final long deadline;
        // 剩余轮数
        private long remainingRounds;

        private HashedWheelTimeout pre;
        private HashedWheelTimeout next;
        private HashedWheelBucket bucket;


        public HashedWheelTimeout(HashedWheelTimer timer, TimeTask task, long deadline) {
            this.timer = timer;
            this.task = task;
            this.deadline = deadline;
        }

        void remove() {
            HashedWheelBucket bucket = this.bucket;
            if (bucket != null) {
                bucket.remove(this);
            } else {
                timer.decrementPendingTimeoutCount();
            }
        }

        void expire() {
            // 修改状态 -- init => expire
            if (!state.compareAndSet(HWT_INIT, HWT_EXPIRE)) {
                return;
            }
            task.run();
        }

        @Override
        public boolean cancel() {
            // 修改状态 -- init => cancel
            if (!state.compareAndSet(HWT_INIT, HWT_CANCEL)) {
                return false;
            }
            // 移除
            remove();
            return true;
        }

        public boolean isCancel() {
            return state.get() == HWT_CANCEL;
        }
    }

    private static class HashedWheelBucket {
        private HashedWheelTimeout head;
        private HashedWheelTimeout tail;

        void addTimeout(HashedWheelTimeout timeout) {
            assert timeout.bucket == null;
            timeout.bucket = this;

            // 桶未初始化
            if (head == null) {
                head = tail = timeout;
            } else {
                // 添加到末尾
                timeout.pre = tail;
                tail.next = timeout;
                tail = timeout;
            }
        }

        HashedWheelTimeout remove(HashedWheelTimeout timeout) {
            HashedWheelTimeout next = timeout.next;
            HashedWheelTimeout pre = timeout.pre;
            if (timeout == head) {
                this.head = next;
            } else {  //
                timeout.pre.next = next;
            }
            if (timeout == tail) {
                this.tail = pre;
            } else {
                timeout.next.pre = pre;
            }

            // help gc
            timeout.next = null;
            timeout.pre = null;
            timeout.bucket = null;

            // 计数
            timeout.timer.decrementPendingTimeoutCount();
            return next;
        }

        public void expireTimeouts() {
            HashedWheelTimeout timeout = head;
            while (timeout != null) {
                HashedWheelTimeout next = timeout.next;

                if (timeout.remainingRounds <= 0) {
                    // expire
                    timeout.expire();
                    next = remove(timeout);
                } else if (timeout.isCancel()) {
                    // unexpired && cancel(运行结束)
                    next = remove(timeout);
                } else {
                    timeout.remainingRounds--;
                }
                timeout = next;
            }

        }
    }

    @Override
    public Timeout createTimeout(TimeTask task, long delay, TimeUnit unit) {
        if (task == null) {
            throw new NullPointerException("task");
        }
        if (unit == null) {
            throw new NullPointerException("unit");
        }
        // 判断队列中任务数是否大于最大任务数
        long pendingTimeoutsCount = incrementPendingTimeoutCount();
        if(pendingTimeoutsCount>maxPendingTimeoutsCount){
            decrementPendingTimeoutCount();
            throw new RejectedExecutionException("Number of pending timeouts ("
                    + pendingTimeoutsCount + ") is greater than or equal to maximum allowed pending "
                    + "timeouts (" + maxPendingTimeoutsCount + ")");
        }


        // ??? 是否需要，可以直接让任务参与过期状态??
//        if(delay<0){
//            throw new IllegalStateException("delay cannot be negative");
//        }

        // deadline>0 说明还未过期， deadline<0说明已经过期，会在HashedWheelBucket.expireTimeouts()计算得到当前桶
        // TODO: 是否加一个状态用来标识【<0】情况
        //  特殊：加入到队列中，已经过期，但是还没到下一个时间片，外部任务执行结束调用cancel
        //  还是将这种特殊情况考虑到容错中，允许这种情况发生。即允许有1个时间片的容错
        long deadline = unit.toNanos(delay)+System.nanoTime()-startTime;
        HashedWheelTimeout timeout = new HashedWheelTimeout(this, task, deadline);
        timeouts.add(timeout);
        return timeout;
    }

    private static class TimerTickerRunnable implements Runnable {
        private static final Integer TTR_INIT = 1;
        private static final Integer TTR_RUNNING = 2;
        private static final Integer TTR_STOP = 3;

        private final AtomicInteger state = new AtomicInteger(TTR_INIT);

        private final HashedWheelTimer timer;

        // 时钟转数
        private long tick;

        TimerTickerRunnable(HashedWheelTimer timer) {
            this.timer = timer;
        }

        @Override
        public void run() {
            // 初始化 startTime
            initializeStartTime();

            do {
                // 1. 暂停一段时间 防止频繁工作
                waitForNextTick();
                // 2. 从队列中取出任务打散到桶中
                scatterToBucket();
                // 3. 扫描当前桶中的元素
                HashedWheelBucket bucket = timer.wheel[(int) (tick & timer.mask)];
                bucket.expireTimeouts();
                tick++;
            } while (state.get() == TTR_RUNNING);
            // TODO: stop后置处理
        }

        private void scatterToBucket() {
            Queue<HashedWheelTimeout> timeouts = timer.timeouts;
            // 一次最多处理10000条数据
            for (int i = 0; i < 10000; i++) {
                HashedWheelTimeout timeout = timeouts.poll();
                if (timeout == null) {
                    // 队列中没有任务
                    break;
                }
                // 是否可以无脑打散？？？ 在expire时进行判断
                if (timeout.isCancel()) {
                    // 在队列中，但是处于cancel状态
                    continue;
                }
                // 打散

                long needTick = timeout.deadline / timer.tickDuration;
                // (needTick - tick)>>timer.wheelBit 等价于 (needTick - tick)/timer.wheel.length
                // 原因: wheel.length 是2的n次方
                timeout.remainingRounds = (needTick - tick) >> timer.wheelBit;
                // needTick<tick时说明已经超时，加入到当前的桶中进行处理
                long ticks = Math.max(needTick, tick);
                int addIdx = (int) (ticks & timer.mask);
                timer.wheel[addIdx].addTimeout(timeout);
            }
        }

        private long waitForNextTick() {
            long deadline = timer.tickDuration * (tick + 1);
            long startTime = timer.startTime;
            for (; ; ) {
                long currentTime = System.nanoTime() - startTime;
                if (currentTime >= deadline) {
                    return currentTime;
                }
                long sleepMs = (deadline - currentTime) / 1000;
                try {
                    Thread.sleep(sleepMs);
                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
                    // TODO: 做相应处理
                }
            }

        }

        private void initializeStartTime() {
            for (; ; ) {
                timer.startTime = System.nanoTime();
                if (timer.startTime != 0) {
                    // 修改为运行状态 -- init=>running
                    if (state.compareAndSet(TTR_INIT, TTR_RUNNING)) {
                        return;
                    } else { // 初始化时未处于init状态
                        throw new IllegalStateException("illegal state");
                    }
                }
            }
        }


    }

    private long decrementPendingTimeoutCount() {
        return pendingTimeoutsCount.decrementAndGet();
    }
    private long incrementPendingTimeoutCount() {
        return pendingTimeoutsCount.incrementAndGet();
    }

}
