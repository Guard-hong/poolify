package cn.poolify.core.trigger.nacos.model;

import lombok.Data;

/**
 * @Author: HCJ
 * @DateTime: 2024/9/14
 * @Description: 监听线程池参数实体
 **/
@Data
public class ThreadPoolConfigEntity {
    /** 应用名 */
    private String applicationName;
    /** 线程名 */
    private String threadPoolName;
    /** 核心线程数 */
    private Integer corePoolSize;
    /** 总线程数 */
    private Integer maximumPoolSize;
    /** 队列容量 */
    private Integer queueCapacity;
}
