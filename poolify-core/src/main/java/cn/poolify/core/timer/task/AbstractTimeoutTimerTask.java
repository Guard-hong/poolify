package cn.poolify.core.timer.task;

import cn.poolify.core.executor.ExecutorWrapper;
import cn.poolify.core.timer.TimerTask;

/**
 * @Author: HCJ
 * @DateTime: 2024/12/21
 * @Description: 将超时的定时任务公共逻辑抽离
 **/
public abstract class AbstractTimeoutTimerTask implements TimerTask{

    // 执行器
    protected ExecutorWrapper executorWrapper;
    // 执行线程
    protected Runnable runnable;

    protected AbstractTimeoutTimerTask(ExecutorWrapper executorWrapper,Runnable runnable){
        this.executorWrapper = executorWrapper;
        this.runnable = runnable;
    }


    @Override
    public void run() {
        doRun(executorWrapper);
    }

    protected abstract void doRun(ExecutorWrapper executor);
}
