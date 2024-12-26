package cn.poolify.core.registry;

import cn.poolify.core.executor.ExecutorWrapper;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: HCJ
 * @DateTime: 2024/9/14
 * @Description: 动态线程池注册和管理
 **/
@Slf4j
public class DtpRegistry {

    private static final Map<String, ExecutorWrapper> DYNAMIC_EXECUTORS = new ConcurrentHashMap<>();

    public static void register(String poolName,ExecutorWrapper executorWrapper){
        DYNAMIC_EXECUTORS.putIfAbsent(poolName,executorWrapper);
    }

}
