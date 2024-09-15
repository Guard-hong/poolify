package cn.poolify.core.registry.impl.nacos;

import cn.poolify.common.constant.Constant;
import cn.poolify.core.config.properties.NacosRegistryProperties;
import cn.poolify.core.registry.IRegistry;
import cn.poolify.core.registry.impl.nacos.model.NaocsAdjustThreadPoolConfig;
import cn.poolify.core.registry.model.entity.ThreadPoolConfigEntity;
import cn.poolify.core.registry.model.val.ReportThreadPoolConfigVO;
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
        configService.publishConfig(nacosRegistryProperties.getDataId()+ Constant.HYPHEN + threadPoolConfigEntity.getThreadPoolName(), nacosRegistryProperties.getGroupId(),
                getPublishJSON(ReportThreadPoolConfigVO.builder()
                        .corePoolSize(threadPoolConfigEntity.getCorePoolSize())
                        .maximumPoolSize(threadPoolConfigEntity.getMaximumPoolSize())
                        .build()));
    }

    @Override
    public void reportAdjustThreadPoolConfig(NaocsAdjustThreadPoolConfig updateThreadPoolConfig) throws NacosException {
        configService.publishConfig(updateThreadPoolConfig.getDataId(), updateThreadPoolConfig.getGroupId(),
                getPublishJSON(ReportThreadPoolConfigVO.builder()
                        .corePoolSize(updateThreadPoolConfig.getCorePoolSize())
                        .maximumPoolSize(updateThreadPoolConfig.getMaximumPoolSize())
                        .build()));
    }

    private static String getPublishJSON(ReportThreadPoolConfigVO reportThreadPoolConfigVO){
        return JSON.toJSONString(reportThreadPoolConfigVO);
    }
}
