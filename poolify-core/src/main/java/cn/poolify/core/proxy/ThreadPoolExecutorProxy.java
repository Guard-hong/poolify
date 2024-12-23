package cn.poolify.core.proxy;

import cn.poolify.core.aware.AwareManager;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author: HCJ
 * @DateTime: 2024/12/22
 * @Description:
 **/
@Slf4j
public class ThreadPoolExecutorProxy extends ThreadPoolExecutor {

    private long queueTimeout;
    private long runTimeout;


    public ThreadPoolExecutorProxy(String name,ThreadPoolExecutor originExecutor){
        super(originExecutor.getCorePoolSize(), originExecutor.getMaximumPoolSize(),
                originExecutor.getKeepAliveTime(TimeUnit.MILLISECONDS), TimeUnit.MILLISECONDS,
                originExecutor.getQueue(), originExecutor.getThreadFactory(),
                originExecutor.getRejectedExecutionHandler());
        allowCoreThreadTimeOut(originExecutor.allowsCoreThreadTimeOut());
        // 关闭原有线程池
        showdownAsync(name,originExecutor);
        originExecutor.shutdown();
    }

    private static void showdownAsync(String name, ThreadPoolExecutor executor) {
        new Thread(()->{
            executor.shutdown();
            log.info("ThreadPoolExecutor: {} showdown",name);
        }).start();
    }


    @Override
    public void execute(Runnable command) {
        AwareManager.executor(this,command);
        super.execute(command);
    }

    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        AwareManager.beforeExecutor(this,r,t);
        super.beforeExecute(t, r);
    }

    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        AwareManager.afterExecutor(this,r);
        super.afterExecute(r, t);
    }

    public long getQueueTimeout() {
        return queueTimeout;
    }

    public void setQueueTimeout(long queueTimeout) {
        this.queueTimeout = queueTimeout;
    }

    public long getRunTimeout() {
        return runTimeout;
    }

    public void setRunTimeout(long runTimeout) {
        this.runTimeout = runTimeout;
    }
}
