package cn.poolify.test.starter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author: HCJ
 * @DateTime: 2024/9/13
 * @Description:
 **/
@SpringBootApplication
//启用nacosConfig注解,该注解最终会扫描加载NacosConfigListenerMethodProcessor类
//@EnableNacosConfig(globalProperties = @NacosProperties(serverAddr = "${spring.cloud.nacos.config.server-addr}"))
public class DynamicThreadPoolTestApplication {
    public static void main(String[] args) {
        SpringApplication.run(DynamicThreadPoolTestApplication.class, args);
    }
}
