package cn.poolify.core.proxy;

import cn.poolify.core.aware.AwareManager;

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author: HCJ
 * @DateTime: 2024/12/22
 * @Description:
 **/
public class ThreadPoolExecutorProxy extends ThreadPoolExecutor {


    public ThreadPoolExecutorProxy(ThreadPoolExecutor originExecutor){
        super(originExecutor.getCorePoolSize(), originExecutor.getMaximumPoolSize(),
                originExecutor.getKeepAliveTime(TimeUnit.MILLISECONDS), TimeUnit.MILLISECONDS,
                originExecutor.getQueue(), originExecutor.getThreadFactory(),
                originExecutor.getRejectedExecutionHandler());
        allowCoreThreadTimeOut(originExecutor.allowsCoreThreadTimeOut());
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
}
