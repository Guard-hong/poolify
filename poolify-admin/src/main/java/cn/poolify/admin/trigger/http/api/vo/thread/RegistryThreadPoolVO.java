package cn.poolify.admin.trigger.http.api.vo.thread;

import lombok.*;

/**
 * @Author: HCJ
 * @DateTime: 2024/9/18
 * @Description:
 **/
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegistryThreadPoolVO {
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
