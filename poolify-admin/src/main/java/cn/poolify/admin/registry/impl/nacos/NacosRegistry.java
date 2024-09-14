package cn.poolify.admin.registry.impl.nacos;

import cn.poolify.admin.registry.IRegistry;
import cn.poolify.admin.registry.model.dto.UpdateThreadPoolConfigDTO;
import cn.poolify.admin.registry.model.entity.ThreadPoolConfigEntity;

import java.util.List;

/**
 * @Author: HCJ
 * @DateTime: 2024/9/14
 * @Description:
 **/
public class NacosRegistry implements IRegistry {
    @Override
    public String mark() {
        return "nacos";
    }

    @Override
    public void reportThreadPool(List<ThreadPoolConfigEntity> threadPoolConfigEntityList) {

    }

    @Override
    public void reportUpdateThreadPoolConfigParameter(UpdateThreadPoolConfigDTO updateThreadPoolConfigDTO) {

    }
}
