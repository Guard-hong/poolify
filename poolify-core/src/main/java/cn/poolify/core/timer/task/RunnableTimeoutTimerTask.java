package cn.poolify.core.timer.task;

import cn.poolify.core.enums.AlarmType;
import cn.poolify.core.executor.wrapper.ExecutorWrapper;
import cn.poolify.core.manager.TransmitterHelper;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author: HCJ
 * @DateTime: 2024/12/21
 * @Description:
 **/
@Slf4j
public class RunnableTimeoutTimerTask extends AbstractTimeoutTimerTask{

    public RunnableTimeoutTimerTask(ExecutorWrapper executorWrapper, Runnable runnable) {
        super(executorWrapper, runnable);
    }

    @Override
    protected void doRun(ExecutorWrapper executor) {
        TransmitterHelper.sendAlarmMsg(executor, AlarmType.RUN_TIMEOUT);
        log.warn("DynamicTp execute, run timeout, " +
                        "tpName: {}, runTimeout: {}ms, " +
                        "poolSize: {} (active: {}, core: {}, max: {}, largest: {}), " +
                        "queue: (currSize: {}, remaining: {})",
                executor.getThreadPoolName(),executor.getRunTimeout(),
                executor.getPoolSize(), executor.getActiveCount(),
                executor.getCorePoolSize(), executor.getMaximumPoolSize(), executor.getLargestPoolSize(),
                executor.getQueue().size(), executor.getQueue().remainingCapacity());
    }
}
