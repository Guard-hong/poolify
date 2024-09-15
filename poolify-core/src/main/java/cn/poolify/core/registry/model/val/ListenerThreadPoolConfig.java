package cn.poolify.core.registry.model.val;

import lombok.*;

/**
 * @Author: HCJ
 * @DateTime: 2024/9/15
 * @Description: 监听线程池数据对象
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ListenerThreadPoolConfig {
    /** 核心线程数 */
    private Integer corePoolSize;
    /** 最大线程数 */
    private Integer maximumPoolSize;
    /** 队列类型 */
    private String queueType;
    /** 队列容量 */
    private Integer queueCapacity;
    /** 拒绝策略 */
    private String rejectedExecutionHandler;
}
