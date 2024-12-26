package cn.poolify.core.executor;

import cn.poolify.core.aware.AwareManager;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Author: HCJ
 * @DateTime: 2024/12/22
 * @Description: 源线程池代理
 **/
@Slf4j
public class ThreadPoolExecutorProxy extends ExecutorWrapper {


    public ThreadPoolExecutorProxy(String name,ThreadPoolExecutor originExecutor){
        super(name,originExecutor);
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
