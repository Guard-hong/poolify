package cn.poolify.core.aware;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Author: HCJ
 * @DateTime: 2024/12/22
 * @Description:
 **/
public interface ExecutorAware {

    void  execute(ThreadPoolExecutor executor,Runnable r);
    Runnable beforeExecuteWrap(ThreadPoolExecutor executor,Runnable r,Thread t);


    Runnable afterExecutor(ThreadPoolExecutor executor, Runnable r);
}
