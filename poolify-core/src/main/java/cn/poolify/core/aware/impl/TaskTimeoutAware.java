package cn.poolify.core.aware.impl;

import cn.poolify.core.aware.ExecutorAware;
import cn.poolify.core.executor.ExecutorWrapper;

/**
 * @Author: HCJ
 * @DateTime: 2024/12/22
 * @Description:
 **/
public class TaskTimeoutAware implements ExecutorAware {
    @Override
    public void execute(ExecutorWrapper executor, Runnable r) {
        executor.startQueueTimeoutTask(r);
    }

    @Override
    public Runnable beforeExecuteWrap(ExecutorWrapper executor, Runnable r, Thread t) {
        executor.cancelQueueTimeoutTask(r);
        executor.startRunTimeoutTask(t,r);
        return r;
    }

    @Override
    public Runnable afterExecutor(ExecutorWrapper executor, Runnable r) {
        executor.cancelRunTimeoutTask(r);
        return r;
    }

}
