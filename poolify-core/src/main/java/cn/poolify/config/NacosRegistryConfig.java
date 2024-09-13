package cn.poolify.config;

import cn.poolify.config.properties.NacosRegistryProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
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
}
