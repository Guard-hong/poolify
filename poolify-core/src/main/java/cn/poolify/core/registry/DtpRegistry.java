package cn.poolify.core.registry;

import cn.poolify.core.monitor.ExecutorMonitor;
import cn.poolify.core.wrapper.ExecutorWrapper;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Author: HCJ
 * @DateTime: 2024/9/14
 * @Description: 动态线程池注册和管理
 **/
@Slf4j
public class DtpRegistry {

    private static final Map<ThreadPoolExecutor, ExecutorWrapper> DYNAMIC_EXECUTORS = new ConcurrentHashMap<>();



    public static ExecutorMonitor getThreadPoolMonitor(ThreadPoolExecutor executor){
        return Optional.ofNullable(DYNAMIC_EXECUTORS.get(executor))
                .map(ExecutorWrapper::getExecutorMonitor)
                .orElseThrow(()-> new NoSuchElementException("ExecutorMonitor not found"));
    }
    public static void register(ExecutorWrapper executorWrapper){
        DYNAMIC_EXECUTORS.putIfAbsent(executorWrapper.getExecutor(),executorWrapper);
    }

}
