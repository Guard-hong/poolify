package cn.poolify.core.aware.impl;

import cn.poolify.core.aware.ExecutorAware;
import cn.poolify.core.monitor.ExecutorMonitor;
import cn.poolify.core.registry.DtpRegistry;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Author: HCJ
 * @DateTime: 2024/12/22
 * @Description:
 **/
public class TaskTimeoutAware implements ExecutorAware {
    @Override
    public void execute(ThreadPoolExecutor executor,Runnable r) {
        ExecutorMonitor monitor = DtpRegistry.getThreadPoolMonitor(executor);
        monitor.startQueueTimeoutTask(r);
    }

    @Override
    public Runnable beforeExecuteWrap(ThreadPoolExecutor executor, Runnable r, Thread t) {
        ExecutorMonitor monitor = DtpRegistry.getThreadPoolMonitor(executor);
        monitor.cancelQueueTimeoutTask(r);
        monitor.startRunTimeoutTask(t,r);
        return r;
    }

    @Override
    public Runnable afterExecutor(ThreadPoolExecutor executor, Runnable r) {
        ExecutorMonitor monitor = DtpRegistry.getThreadPoolMonitor(executor);
        monitor.cancelRunTimeoutTask(r);
        return r;
    }

}
