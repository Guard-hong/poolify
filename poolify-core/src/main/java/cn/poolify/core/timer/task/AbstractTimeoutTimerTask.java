package cn.poolify.core.timer.task;

import cn.poolify.core.monitor.ThreadPoolMonitor;
import cn.poolify.core.timer.TimerTask;
import cn.poolify.core.wrapper.ExecutorWrapper;

/**
 * @Author: HCJ
 * @DateTime: 2024/12/21
 * @Description: 将超时的定时任务公共逻辑抽离
 **/
public abstract class AbstractTimeoutTimerTask implements TimerTask {

    // 执行器
    private ExecutorWrapper executorWrapper;
    // 执行线程
    private Runnable runnable;
    @Override
    public void run(ExecutorWrapper executorWrapper,Runnable runnable) {
        ThreadPoolMonitor monitor = executorWrapper.getThreadPoolMonitor();
        if(monitor == null) return ;
        doRun(monitor);
    }

    protected abstract void doRun(ThreadPoolMonitor monitor);
}
