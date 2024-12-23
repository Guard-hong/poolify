package cn.poolify.core.config;

import cn.poolify.core.config.properties.NacosRegistryProperties;
import cn.poolify.core.registry.DtpRegistry;
import cn.poolify.core.trigger.listener.nacos.NacosThreadPoolConfigAdjustListener;
import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.exception.NacosException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: HCJ
 * @DateTime: 2024/9/14
 * @Description:
 **/
@Slf4j
@Configuration
@EnableConfigurationProperties(NacosRegistryProperties.class)
public class NacosRegistryConfig {

    @Bean
    public NacosThreadPoolConfigAdjustListener nacosThreadPoolConfigAdjustListener(NacosRegistryProperties nacosRegistryProperties,
                                                                                   ConfigService configService,
                                                                                   DtpRegistry dtpRegistry){
        return new NacosThreadPoolConfigAdjustListener(nacosRegistryProperties,configService, dtpRegistry);
    }



    @Bean
    public ConfigService configService(NacosRegistryProperties nacosRegistryProperties) throws NacosException {
        String addr = nacosRegistryProperties.getHost() + ":" + nacosRegistryProperties.getPort();
        return NacosFactory.createConfigService(addr);
    }

}
