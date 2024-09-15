package cn.poolify.core.registry;

import cn.poolify.common.exception.DynamicThreadPoolException;
import cn.poolify.common.exception.ErrorCode;
import cn.poolify.core.config.properties.DynamicThreadProperties;
import cn.poolify.core.registry.model.val.CollectionThreadPoolConfigVO;
import cn.poolify.core.registry.model.val.RegistryThreadPoolVO;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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

    private final DynamicThreadProperties dynamicThreadProperties;
    private final Map<String, ThreadPoolExecutor> DYNAMIC_THREAD_POOLS = new ConcurrentHashMap<>();

    public DynamicThreadPoolRegistry(DynamicThreadProperties dynamicThreadProperties) {
        this.dynamicThreadProperties = dynamicThreadProperties;
    }

    // 注册线程池
    public void register(String threadPoolId, ThreadPoolExecutor executor) {
        DYNAMIC_THREAD_POOLS.put(threadPoolId, executor);
    }

    // 获取线程池
    public ThreadPoolExecutor getThreadPoolExecutor(String threadPoolId) {
        return DYNAMIC_THREAD_POOLS.get(threadPoolId);
    }

    // 获取所有注册的线程池
    public Map<String, ThreadPoolExecutor> getAllThreadPools() {
        return Collections.unmodifiableMap(DYNAMIC_THREAD_POOLS);
    }

    /**
     * 收集所有线程池参数
     *
     * @return
     */
    public List<CollectionThreadPoolConfigVO> getAllThreadPoolConfig() {
        List<CollectionThreadPoolConfigVO> threadPoolConfigVOS = new ArrayList<>();
        DYNAMIC_THREAD_POOLS.forEach((key, val) -> {
            threadPoolConfigVOS.add(CollectionThreadPoolConfigVO.builder()
                    .applicationName(dynamicThreadProperties.getApplicationName())
                    .threadPoolName(key)
                    .activeThreadCount(val.getActiveCount())
                    .queueSize(val.getPoolSize())
                    .completedTaskCount(val.getCompletedTaskCount())
                    .build());
        });
        return threadPoolConfigVOS;
    }

    // 修改线程池参数
    public void updateThreadPoolParameter(String threadPoolName, RegistryThreadPoolVO registryThreadPoolVO) {
        try {
            ThreadPoolExecutor threadPoolExecutor = getThreadPoolExecutor(threadPoolName);
            threadPoolExecutor.setCorePoolSize(registryThreadPoolVO.getCorePoolSize());
            threadPoolExecutor.setMaximumPoolSize(registryThreadPoolVO.getMaximumPoolSize());
        } catch (NullPointerException e) {
            log.error("线程池: {}不存在", threadPoolName);
            throw new DynamicThreadPoolException(ErrorCode.THREAD_POOL_NOT_EXIST);
        }
    }
}
