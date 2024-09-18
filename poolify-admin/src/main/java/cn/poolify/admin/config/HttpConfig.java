package cn.poolify.admin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * @Author: HCJ
 * @DateTime: 2024/9/18
 * @Description:
 **/
@Configuration
public class HttpConfig {

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .defaultHeader("Content-Type", "application/json") // 默认请求头
                .build(); // 默认接受头
    }
}
