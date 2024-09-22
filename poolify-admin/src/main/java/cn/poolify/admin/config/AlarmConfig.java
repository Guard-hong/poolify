package cn.poolify.admin.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @Author: HCJ
 * @DateTime: 2024/9/22
 * @Description:
 **/
@Configuration
@ConditionalOnProperty(prefix = "poolify.alarm", name = "enabled", havingValue = "true")
@EnableScheduling
@EnableConfigurationProperties(AlarmProperties.class)
public class AlarmConfig {


}
