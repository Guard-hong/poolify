package cn.poolify.core.timer.task;

import cn.poolify.core.monitor.ExecutorMonitor;
import cn.poolify.core.wrapper.ExecutorWrapper;

/**
 * @Author: HCJ
 * @DateTime: 2024/12/21
 * @Description:
 **/
public class RunnableTimeoutTimerTask extends AbstractTimeoutTimerTask{

    public RunnableTimeoutTimerTask(ExecutorWrapper executorWrapper, Runnable runnable) {
        super(executorWrapper, runnable);
    }

    @Override
    protected void doRun(ExecutorMonitor monitor) {
        // TODO: 报警
    }
}
