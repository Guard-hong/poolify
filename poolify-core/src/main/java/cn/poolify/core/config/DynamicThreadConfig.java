package cn.poolify.core.config;

import cn.poolify.core.aop.DynamicThreadPoolProcessor;
import cn.poolify.core.config.properties.DynamicThreadProperties;
import cn.poolify.core.registry.DynamicThreadPoolRegistry;
import cn.poolify.core.registry.IRegistry;
import cn.poolify.core.registry.impl.nacos.NacosRegistry;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @Author: HCJ
 * @DateTime: 2024/9/15
 * @Description: 动态线程池配置类
 **/
@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableConfigurationProperties(DynamicThreadProperties.class)
public class DynamicThreadConfig {

    @Bean
    public IRegistry nacos(){
        return new NacosRegistry();
    }
    @Bean
    public DynamicThreadPoolRegistry dynamicThreadPoolRegistry(DynamicThreadProperties dynamicThreadProperties){
        return new DynamicThreadPoolRegistry(dynamicThreadProperties);
    }

    @Bean
    public DynamicThreadPoolProcessor dynamicThreadPoolProcessor(){
        return new DynamicThreadPoolProcessor();
    }

}
