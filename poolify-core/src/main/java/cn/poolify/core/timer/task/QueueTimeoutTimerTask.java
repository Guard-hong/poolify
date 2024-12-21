package cn.poolify.core.timer.task;

import cn.poolify.core.monitor.ThreadPoolMonitor;
import cn.poolify.core.wrapper.ExecutorWrapper;


/**
 * @Author: HCJ
 * @DateTime: 2024/12/21
 * @Description:
 **/
public class QueueTimeoutTimerTask extends AbstractTimeoutTimerTask {
    public QueueTimeoutTimerTask(ExecutorWrapper executorWrapper, Runnable runnable) {
        super(executorWrapper, runnable);
    }

    @Override
    protected void doRun(ThreadPoolMonitor monitor) {
        // TODO: 报警
    }
}
