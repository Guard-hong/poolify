package cn.poolify.test.starter.config;

import com.alibaba.nacos.api.config.ConfigFactory;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.exception.NacosException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * @Author: HCJ
 * @DateTime: 2024/9/13
 * @Description:
 **/
@Configuration
public class NacosConfig {
//    @Resource
//    private NacosProperties nacosProperties;

    @Bean
    public ConfigService configService() throws Exception {
        return ConfigFactory.createConfigService("localhost:8848"); // 替换为你的 Nacos 服务器地址
    }

}
