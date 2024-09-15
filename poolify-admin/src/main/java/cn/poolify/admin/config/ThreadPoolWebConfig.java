package cn.poolify.admin.config;

import cn.poolify.admin.config.properties.ThreadPoolWebConfigProperties;
import cn.poolify.admin.trigger.http.controller.ThreadPoolController;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * @Author: HCJ
 * @DateTime: 2024/9/13
 * @Description:
 **/
@MapperScan("cn.poolify.admin.persistent.mapper")
@Configuration
@EnableConfigurationProperties(ThreadPoolWebConfigProperties.class)
public class ThreadPoolWebConfig {

    @Bean
    public ThreadPoolFrontendConfig threadPoolFrontendConfig(){
        return new ThreadPoolFrontendConfig();
    }

    @Bean
    public ThreadPoolController threadPoolController(){
        return new ThreadPoolController();
    }
}
