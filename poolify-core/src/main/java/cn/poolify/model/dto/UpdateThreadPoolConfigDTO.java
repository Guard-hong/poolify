package cn.poolify.model.dto;

import cn.poolify.model.ThreadPoolConfigEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: HCJ
 * @DateTime: 2024/9/14
 * @Description:
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateThreadPoolConfigDTO {

    private String applicationName;
    private String threadPoolName;
    private Integer corePoolSize;
    private Integer maximumPoolSize;
    private Integer queueCapacity;

    public static UpdateThreadPoolConfigDTO buildUpdateThreadPoolConfigDTO(
            ThreadPoolConfigEntity threadPoolConfigEntity ) {
        return new UpdateThreadPoolConfigDTO(
                threadPoolConfigEntity.getApplicationName(),
                threadPoolConfigEntity.getThreadPoolName(),
                threadPoolConfigEntity.getCorePoolSize(),
                threadPoolConfigEntity.getMaximumPoolSize(),
                threadPoolConfigEntity.getQueueSize() + threadPoolConfigEntity.getRemainingCapacity()
        );
    }
}
