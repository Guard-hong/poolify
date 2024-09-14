package cn.poolify.core.config;

import cn.poolify.core.config.properties.NacosRegistryProperties;
import cn.poolify.core.trigger.nacos.linstener.NacosThreadPoolConfigAdjustListener;
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
    public NacosThreadPoolConfigAdjustListener nacosThreadPoolConfigAdjustListener(NacosRegistryProperties nacosRegistryProperties){
        return new NacosThreadPoolConfigAdjustListener(nacosRegistryProperties);
    }
}
