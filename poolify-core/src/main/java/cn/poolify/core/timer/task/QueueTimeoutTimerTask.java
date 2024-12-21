package cn.poolify.core.timer.task;

import cn.poolify.core.monitor.ThreadPoolMonitor;
import cn.poolify.core.timer.TimerTask;

/**
 * @Author: HCJ
 * @DateTime: 2024/12/21
 * @Description:
 **/
public class QueueTimeoutTimerTask extends AbstractTimeoutTimerTask {
    @Override
    protected void doRun(ThreadPoolMonitor monitor) {
        // TODO: 报警
    }
}
