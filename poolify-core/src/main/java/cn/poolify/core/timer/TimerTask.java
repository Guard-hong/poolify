package cn.poolify.core.timer;

import cn.poolify.core.wrapper.ExecutorWrapper;

/**
 * @Author: HCJ
 * @DateTime: 2024/12/17
 * @Description:
 **/
public interface TimerTask {

    void run(ExecutorWrapper executorWrapper, Runnable runnable);
}
