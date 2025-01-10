package cn.poolify.core.executor.wrapper;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Author: HCJ
 * @DateTime: 2024/12/22
 * @Description: 源线程池代理
 **/
@Slf4j
public class ExecutorProxy extends ExecutorWrapper {

    public ExecutorProxy(String name, ThreadPoolExecutor originExecutor, long runTimeout, long queueTimeout) {
        super(name, runTimeout, queueTimeout, originExecutor);
    }

    public static ExecutorProxy of(String name, ThreadPoolExecutor originExecutor, long runTimeout, long queueTimeout) {
        return new ExecutorProxy(name, originExecutor, runTimeout, queueTimeout);
    }



}
