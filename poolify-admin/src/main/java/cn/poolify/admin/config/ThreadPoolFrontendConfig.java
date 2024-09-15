package cn.poolify.admin.config;

import cn.poolify.admin.config.properties.ThreadPoolWebConfigProperties;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * @Author: HCJ
 * @DateTime: 2024/9/15
 * @Description:
 **/
@MapperScan("cn.poolify.admin.persistent.mapper")
@Configuration
public class ThreadPoolFrontendConfig implements WebMvcConfigurer {

    @Resource
    private ThreadPoolWebConfigProperties threadPoolWebConfigProperties;
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler(threadPoolWebConfigProperties.getContextPath() + "/**")
                .addResourceLocations("classpath:/web/");
        registry
                .addResourceHandler("/f8e5c81e18c8522/**")
                .addResourceLocations("classpath:/web/");
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addRedirectViewController(
                threadPoolWebConfigProperties.getContextPath(),
                threadPoolWebConfigProperties.getContextPath() + "/index.html"
        );
    }
}
