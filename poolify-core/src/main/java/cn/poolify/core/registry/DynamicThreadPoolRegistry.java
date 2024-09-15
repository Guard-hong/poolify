package cn.poolify.core.registry;

import cn.poolify.common.exception.DynamicThreadPoolException;
import cn.poolify.common.exception.ErrorCode;
import cn.poolify.core.registry.model.val.ListenerThreadPoolConfig;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Author: HCJ
 * @DateTime: 2024/9/14
 * @Description: 动态线程池注册和管理
 **/
@Slf4j
public class DynamicThreadPoolRegistry {
    private static final Map<String, ThreadPoolExecutor> DYNAMIC_THREAD_POOLS = new ConcurrentHashMap<>();

    // 注册线程池
    public static void register(String threadPoolId, ThreadPoolExecutor executor) {
        DYNAMIC_THREAD_POOLS.put(threadPoolId, executor);
    }

    // 获取线程池
    public static ThreadPoolExecutor getThreadPoolExecutor(String threadPoolId) {
        return DYNAMIC_THREAD_POOLS.get(threadPoolId);
    }

    // 获取所有注册的线程池
    public static Map<String, ThreadPoolExecutor> getAllThreadPools() {
        return Collections.unmodifiableMap(DYNAMIC_THREAD_POOLS);
    }

    // 修改线程池参数
    public static void updateThreadPoolParameter(String threadPoolName, ListenerThreadPoolConfig reportThreadPoolConfigDTO){
        try {
            ThreadPoolExecutor threadPoolExecutor = getThreadPoolExecutor(threadPoolName);
            threadPoolExecutor.setCorePoolSize(reportThreadPoolConfigDTO.getCorePoolSize());
            threadPoolExecutor.setMaximumPoolSize(reportThreadPoolConfigDTO.getMaximumPoolSize());
        }catch (NullPointerException e){
            log.error("线程池: {}不存在",threadPoolName);
            throw new DynamicThreadPoolException(ErrorCode.THREAD_POOL_NOT_EXIST);
        }
    }
}
