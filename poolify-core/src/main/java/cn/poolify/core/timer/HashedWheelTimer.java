package cn.poolify.core.timer;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @Author: HCJ
 * @DateTime: 2024/12/17
 * @Description:
 **/
public class HashedWheelTimer implements Timer{
//    private static Integer INIT = 0;
//    private static Integer START = 1;
//    private static Integer STOP = 2;
//
//    // 计时器状态
//    private static Integer state = INIT;
//
//    private static AtomicIntegerFieldUpdater<HashedWheelTimer> STATE_UPDATER =
//            AtomicIntegerFieldUpdater.newUpdater(HashedWheelTimer.class, "state");

    // 构造扫描线程
    private TimerTickerRunnable tickerRunnable = new TimerTickerRunnable(this);
    private Thread scanThread = new Thread(tickerRunnable);


    // 工作队列 -- 监控的线程池队列
    private static Queue<HashedWheelTimeout> timeouts = new LinkedBlockingQueue<>();
    //
    private HashedWheelBucket[] wheel;
    // 记录队列中有多少个任务
    AtomicLong pendingTimeoutCount = new AtomicLong(0);
    // 队列中最多可以有多少任务
    long maxPendingTimeoutCount;

    volatile long startTime;



    private static class HashedWheelTimeout implements Timeout{
        // 定义状态
        private static Integer HWT_INIT = 1;
        private static Integer HWT_CANCEL = 2;
        private static Integer HWT_EXPIRE = 3;

        private AtomicInteger state;
        // 任务
        private TimeTask task;
        // 期限
        private long deadline;
        // 剩余轮数
        private long remainingRounds;
        HashedWheelTimer timer;

        HashedWheelTimeout pre;
        HashedWheelTimeout next;
        HashedWheelBucket bucket;


        public HashedWheelTimeout(HashedWheelTimer timer,TimeTask task, long deadline) {
            this.timer = timer;
            this.task = task;
            this.deadline = deadline;
        }

        void remove(){
            HashedWheelBucket bucket = this.bucket;
            if(bucket != null){
                bucket.remove(this);
            }else{
                timer.decrementPendingTimeoutCount();
            }
        }

        void expire(){
            // 修改状态 -- init => expire
            if(!state.compareAndSet(HWT_INIT,HWT_EXPIRE)){
                return ;
            }
            task.run();
        }

        @Override
        public boolean cancel() {
            // 修改状态 -- init => cancel
            if(!state.compareAndSet(HWT_INIT,HWT_CANCEL)){
                return false;
            }
            // 移除
            remove();
            return true;
        }
    }

    private static class HashedWheelBucket{
        private HashedWheelTimeout head;
        private HashedWheelTimeout tail;

        void addTimeout(HashedWheelTimeout timeout){
            assert timeout.bucket == null;
            timeout.bucket = this;

            // 桶未初始化
            if(head == null){
                head = tail = timeout;
            }else {
                // 添加到末尾
                timeout.pre = tail;
                tail.next = timeout;
                tail = timeout;
            }
        }

        void remove(HashedWheelTimeout timeout){
            HashedWheelTimeout next = timeout.next;
            HashedWheelTimeout pre = timeout.pre;
            if(timeout == head){
                this.head = next;
            } else{  //
                timeout.pre.next = next;
            }
            if(timeout == tail){
                this.tail = pre;
            }else{
                timeout.next.pre = pre;
            }

            // help gc
            timeout.next = null;
            timeout.pre = null;
            timeout.bucket = null;

            // 计数
            timeout.timer.decrementPendingTimeoutCount();

        }
    }

    @Override
    public Timeout createTimeout(TimeTask timeTask, long delay, TimeUnit unit) {
        // TODO: 校验 =》 null值校验 && 任务数校验

        // todo 根据delay和unit和当前时间计算这个任务的最后期限 deadline

        return null;
    }

    private static class TimerTickerRunnable implements Runnable{
        private static Integer TTR_INIT = 1;
        private static Integer TTR_RUNNING = 2;
        private static Integer TTR_STOP = 3;

        private AtomicInteger state;
        
        private HashedWheelTimer timer;

        TimerTickerRunnable(HashedWheelTimer timer) {
            this.timer = timer;
        }

        @Override
        public void run() {
            // 初始化 startTime
            initializeStartTime();

            do {

            } while(state.get() == TTR_RUNNING); // TODO: 判断线程状态处于运行状态
            // TODO: stop后置处理
        }

        void initializeStartTime(){
            for(;;){
                timer.startTime = System.nanoTime();
                if(timer.startTime != 0) {
                    // 修改为运行状态 -- init=>running
                    if(state.compareAndSet(TTR_INIT,TTR_RUNNING)){
                        return ;
                    }else{ // 初始化时未处于init状态
                        throw new IllegalStateException("cannot be started once stopped");
                    }
                }
            }
        }


    }

    long decrementPendingTimeoutCount(){
        return pendingTimeoutCount.decrementAndGet();
    }

}
