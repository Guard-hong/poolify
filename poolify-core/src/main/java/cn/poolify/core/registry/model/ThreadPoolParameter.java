package cn.poolify.core.registry.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: HCJ
 * @DateTime: 2024/9/14
 * @Description: 线程池参数
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ThreadPoolParameter {

    /** 核心线程数 */
    private Integer corePoolSize;
    /** 总线程数 */
    private Integer maximumPoolSize;
    /** 队列容量 */
    private Integer queueCapacity;
}
