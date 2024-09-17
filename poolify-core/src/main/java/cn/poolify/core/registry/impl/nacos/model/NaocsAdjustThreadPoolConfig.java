package cn.poolify.core.registry.impl.nacos.model;

import lombok.*;

/**
 * @Author: HCJ
 * @DateTime: 2024/9/15
 * @Description:
 **/
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NaocsAdjustThreadPoolConfig {

    private String threadPoolName;
    private String groupId;
    private Integer corePoolSize;
    private Integer maximumPoolSize;
    private Integer queueCapacity;
}
