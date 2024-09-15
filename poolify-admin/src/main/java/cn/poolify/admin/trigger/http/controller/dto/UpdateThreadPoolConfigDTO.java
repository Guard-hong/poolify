package cn.poolify.admin.trigger.http.controller.dto;

import lombok.Data;

/**
 * @Author: HCJ
 * @DateTime: 2024/9/15
 * @Description: 修改配置
 **/
@Data
public class UpdateThreadPoolConfigDTO {

    /** 应用名 */
    private String applicationName;
    /**
     * 线程池名称，用于标识数据属于哪个线程池
     */
    private String threadPoolName;
    /** 核心线程数 */
    private Integer corePoolSize;
    /** 最大线程数 */
    private Integer maximumPoolSize;
    /** 队列类型 */
    private String queueType;
    /** 队列容量 */
    private Integer queueCapacity;
    /** 拒绝策略 */
    private String rejectedPolicy;
}
