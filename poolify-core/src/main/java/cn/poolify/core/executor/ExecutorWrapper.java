package cn.poolify.core.executor;

import cn.poolify.core.manager.ContextManagerHelper;
import cn.poolify.core.timer.HashedWheelTimer;
import cn.poolify.core.timer.Timeout;
import cn.poolify.core.timer.TimerTask;
import cn.poolify.core.timer.task.QueueTimeoutTimerTask;
import cn.poolify.core.timer.task.RunnableTimeoutTimerTask;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.lang.ref.SoftReference;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.*;
import java.util.concurrent.atomic.LongAdder;

/**
 * @Author: HCJ
 * @DateTime: 2024/12/26
 * @Description: 增强线程池基类
 **/
@Getter
@Slf4j
public class ExecutorWrapper extends ThreadPoolExecutor {

    /**
     * 线程池名称
     */
    @Setter
    private String threadPoolName;
    /**
     * 任务运行超时时间，单位ms
     */
    @Setter
    private long runTimeout = 0;

    /**
     * 任务等待超时时间，单位ms
     */
    @Setter
    private long queueTimeout = 0;

    /**
     * 拒绝任务数
     */
    private final LongAdder rejectCount = new LongAdder();

    /**
     * 运行超时任务数
     */
    private final LongAdder runTimeoutCount = new LongAdder();

    /**
     * 等待超时任务数
     */
    private final LongAdder queueTimeoutCount = new LongAdder();

    /**
     * k->Runnable v->Timeout. ps：线程由于异常退出 k=null,v不能正常gc。参考ThreadLocal k-v设计
     */
    private final Map<Runnable, SoftReference<Timeout>> queueTimeoutMap = new ConcurrentHashMap<>();

    /**
     * k->Runnable v->Timeout. ps：线程由于异常退出 k=null,v不能正常gc。参考ThreadLocal k-v设计
     */
    private final Map<Runnable, SoftReference<Timeout>> runTimeoutMap = new ConcurrentHashMap<>();

    /**
     * 用于代理
     * @param name
     * @param originExecutor
     */
    public ExecutorWrapper(String name, ThreadPoolExecutor originExecutor){
        this(originExecutor.getCorePoolSize(), originExecutor.getMaximumPoolSize(),
                originExecutor.getKeepAliveTime(TimeUnit.MILLISECONDS), TimeUnit.MILLISECONDS,
                originExecutor.getQueue(), originExecutor.getThreadFactory(),
                originExecutor.getRejectedExecutionHandler());
        allowCoreThreadTimeOut(originExecutor.allowsCoreThreadTimeOut());
        this.threadPoolName = name;
        // 关闭原有线程池
        showdownAsync(name,originExecutor);
    }

    /**
     * 用于配置创建
     * @param corePoolSize
     * @param maximumPoolSize
     * @param keepAliveTime
     * @param unit
     * @param queue
     * @param factory
     * @param rejectedExecutionHandler
     */
    public ExecutorWrapper(int corePoolSize, int maximumPoolSize, long keepAliveTime,
                           TimeUnit unit, BlockingQueue<Runnable> queue, ThreadFactory factory,
                           RejectedExecutionHandler rejectedExecutionHandler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, queue, factory, rejectedExecutionHandler);
    }


    private static void showdownAsync(String name, ThreadPoolExecutor executor) {
        new Thread(()->{
            executor.shutdown();
            log.info("ThreadPoolExecutor: {} showdown",name);
        }).start();
    }


    public void startQueueTimeoutTask(Runnable r){
        // 设置的超时时间不符合
        if(queueTimeout <= 0) return ;
        HashedWheelTimer timer = ContextManagerHelper.geBean(HashedWheelTimer.class);
        TimerTask task = new QueueTimeoutTimerTask(this,r);
        queueTimeoutMap.put(r, new SoftReference<>(timer.createTimeout(task,queueTimeout, TimeUnit.MICROSECONDS)));
    }
    public void cancelQueueTimeoutTask(Runnable r){
        Optional.ofNullable(queueTimeoutMap.get(r))
                .map(SoftReference::get)
                .ifPresent(Timeout::cancel);
    }


    public void startRunTimeoutTask(Thread t,Runnable r){
        // 设置的超时时间不符合
        if(runTimeout <= 0) return ;
        HashedWheelTimer timer = ContextManagerHelper.geBean(HashedWheelTimer.class);
        TimerTask task = new RunnableTimeoutTimerTask(this,r);
        runTimeoutMap.put(r, new SoftReference<>(timer.createTimeout(task,runTimeout, TimeUnit.MICROSECONDS)));
    }

    public void cancelRunTimeoutTask(Runnable r){
        Optional.ofNullable(runTimeoutMap.get(r))
                .map(SoftReference::get)
                .ifPresent(Timeout::cancel);
    }



}
