package cn.poolify.core.aware;

import cn.poolify.core.executor.ExecutorWrapper;

/**
 * @Author: HCJ
 * @DateTime: 2024/12/22
 * @Description:
 **/
public interface ExecutorAware {

    void  execute(ExecutorWrapper executor, Runnable r);
    Runnable beforeExecuteWrap(ExecutorWrapper executor,Runnable r,Thread t);


    Runnable afterExecutor(ExecutorWrapper executor, Runnable r);
}
