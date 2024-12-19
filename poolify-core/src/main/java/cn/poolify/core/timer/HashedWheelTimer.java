package cn.poolify.core.timer;

import java.util.HashMap;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @Author: HCJ
 * @DateTime: 2024/12/17
 * @Description:
 **/
public class HashedWheelTimer implements Timer {

    private static final int MAX_CAPACITY = 1<<30;
    // 最大一个时钟走200ms
    private static final long MAX_TICK_DURATION = TimeUnit.MICROSECONDS.toNanos(200);

    // 构造扫描线程
    private final TimerTickerRunnable tickerRunnable = new TimerTickerRunnable(this);
    private final Thread scanThread;


    // 工作队列 -- 监控的线程池队列
    private final Queue<HashedWheelTimeout> timeouts = new LinkedBlockingQueue<>();
    // 桶数组
    private final HashedWheelBucket[] wheel;
    // wheel是2的多少次方
    private final int wheelBit;
    // 掩码
    private final int mask;
    // 记录队列中有多少个任务
    private AtomicLong pendingTimeoutCount = new AtomicLong(0);
    // 队列中最多可以有多少任务
    private final long maxPendingTimeoutCount;
    // 一次运行的持续时间
    private final long tickDuration;

    private volatile long startTime;



    public HashedWheelTimer(ThreadFactory threadFactory,
                            long tickDuration,
                            TimeUnit unit,
                            long maxPendingTimeoutCount,
                            int initialCapacity
                            ){
        if (threadFactory == null) {
            throw new NullPointerException("threadFactory");
        }
        if (unit == null) {
            throw new NullPointerException("unit");
        }
        this.tickDuration = Math.max(MAX_TICK_DURATION,unit.toNanos(tickDuration));
        this.scanThread = threadFactory.newThread(tickerRunnable);
        this.maxPendingTimeoutCount = maxPendingTimeoutCount;

        // wheel 相关初始化
        this.wheelBit = Integer.numberOfLeadingZeros(initialCapacity - 1)+1;
        int cap = 1<<this.wheelBit;
        if(cap>MAX_CAPACITY){
            throw new IllegalStateException("exceeding maximum capacity");
        }
        this.wheel = new HashedWheelBucket[cap];
        this.mask = cap-1;

        // 扫描线程启动
        scanThread.start();
    }



    private static class HashedWheelTimeout implements Timeout {
        // 定义状态
        private static Integer HWT_INIT = 1;
        private static Integer HWT_CANCEL = 2;
        private static Integer HWT_EXPIRE = 3;

        private AtomicInteger state = new AtomicInteger(HWT_INIT);
        // 任务
        private TimeTask task;
        // 期限
        private long deadline;
        // 剩余轮数
        private long remainingRounds;
        private HashedWheelTimer timer;
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
    public Timeout createTimeout(TimeTask timeTask, long delay, TimeUnit unit) {
        // TODO: 校验 =》 null值校验 && 任务数校验

        // todo 根据delay和unit和当前时间计算这个任务的最后期限 deadline

        return null;
    }

    private static class TimerTickerRunnable implements Runnable {
        private static Integer TTR_INIT = 1;
        private static Integer TTR_RUNNING = 2;
        private static Integer TTR_STOP = 3;

        private AtomicInteger state = new AtomicInteger(TTR_INIT);

        private HashedWheelTimer timer;

        //
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
                // TODO: 后续将 wheel.length 设为 2^n ，这里可以使用位运算 >>n
                timeout.remainingRounds = (needTick - tick) / timer.wheel.length;
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

    long decrementPendingTimeoutCount() {
        return pendingTimeoutCount.decrementAndGet();
    }

}
