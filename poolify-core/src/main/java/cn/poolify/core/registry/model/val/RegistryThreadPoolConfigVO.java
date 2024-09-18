package cn.poolify.core.registry.model.val;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 存储线程池的配置信息表
 * @TableName thread_pool_config
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegistryThreadPoolConfigVO implements Serializable {

    /**
     * 注册中心类型
     */
    private String registryType;
    /**
     * 对应core地址，用于回调修改线程参数
     */
    private String addr;

    /**
     * 应用名称，用于标识数据属于哪个应用
     */
    private String applicationName;

    /**
     * 线程池的唯一名称，用于区分不同的线程池
     */
    private String threadPoolName;

    /**
     * 核心线程数
     */
    private Integer corePoolSize;

    /**
     * 最大线程数
     */
    private Integer maximumPoolSize;

    /**
     * 队列类型
     */
    private String queueType;

    /**
     * 任务队列容量
     */
    private Integer queueCapacity;

    /**
     * 线程的空闲保持时间
     */
    private Integer keepAliveTime;

    /**
     * 线程的空闲保持时间单位 --枚举
     */
    private Integer keepAliveTimeUnit;

    /**
     * 任务拒绝策略，如：Abort、CallerRuns、Discard、DiscardOldest
     */
    private String rejectedPolicy;

}