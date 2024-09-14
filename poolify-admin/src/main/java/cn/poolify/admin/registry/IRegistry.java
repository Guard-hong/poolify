package cn.poolify.admin.registry;


import cn.poolify.admin.registry.model.entity.ThreadPoolConfigEntity;
import cn.poolify.admin.registry.model.dto.UpdateThreadPoolConfigDTO;

import java.util.List;

/**
 * @Author: HCJ
 * @DateTime: 2024/9/14
 * @Description:
 **/
public interface IRegistry {
    String mark();

    /**
     * 上报全部线程池配置信息
     * @param threadPoolConfigEntityList 线程池列表
     */
    void reportThreadPool(List<ThreadPoolConfigEntity> threadPoolConfigEntityList);

    /**
     * 上报更新线程池配置参数
     * @param updateThreadPoolConfigDTO 更新线程池配置传输对象
     */
    void reportUpdateThreadPoolConfigParameter(UpdateThreadPoolConfigDTO updateThreadPoolConfigDTO);
}
