package cn.poolify.core.registry.impl.nacos;

import cn.poolify.common.constant.Constant;
import cn.poolify.core.config.properties.NacosRegistryProperties;
import cn.poolify.core.registry.IRegistry;
import cn.poolify.core.registry.impl.nacos.model.NaocsAdjustThreadPoolConfig;
import cn.poolify.core.registry.model.entity.ThreadPoolConfigEntity;
import cn.poolify.core.registry.model.entity.RegistryThreadPool;
import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.exception.NacosException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Author: HCJ
 * @DateTime: 2024/9/14
 * @Description:
 **/
@Slf4j
@Component("nacos")
public class NacosRegistry implements IRegistry<NaocsAdjustThreadPoolConfig> {

    @Resource
    private ConfigService configService;

    @Resource
    private NacosRegistryProperties nacosRegistryProperties;

    @Override
    public String mark() {
        return "nacos";
    }

    @Override
    public void reportThreadPool(ThreadPoolConfigEntity threadPoolConfigEntity) throws NacosException {
        configService.publishConfig(threadPoolConfigEntity.getThreadPoolName(), nacosRegistryProperties.getGroupId(),
                getPublishJSON(RegistryThreadPool.builder()
                        .corePoolSize(threadPoolConfigEntity.getCorePoolSize())
                        .maximumPoolSize(threadPoolConfigEntity.getMaximumPoolSize())
                        .build()));
    }

    @Override
    public void adjustThreadPoolConfig(NaocsAdjustThreadPoolConfig updateThreadPoolConfig) throws NacosException {
        configService.publishConfig(updateThreadPoolConfig.getThreadPoolName(), updateThreadPoolConfig.getGroupId(),
                getPublishJSON(RegistryThreadPool.builder()
                        .corePoolSize(updateThreadPoolConfig.getCorePoolSize())
                        .maximumPoolSize(updateThreadPoolConfig.getMaximumPoolSize())
                        .build()));
    }

    @Override
    public RegistryThreadPool queryThreadPoolConfig(String applicationName, String beanName) throws NacosException {
        String threadPoolConfigJSON = configService.getConfig(beanName, applicationName, 5000);
        return JSON.parseObject(threadPoolConfigJSON, RegistryThreadPool.class);
    }

    private static String getPublishJSON(RegistryThreadPool registryThreadPool){
        return JSON.toJSONString(registryThreadPool);
    }
}
