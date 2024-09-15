package cn.poolify.core.config;

import cn.poolify.core.config.properties.ManagementProperties;
import cn.poolify.core.trigger.IThreadPoolDataCollectionJob;
import cn.poolify.core.trigger.job.nacos.NacosThreadDataCollectionJob;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * @Author: HCJ
 * @DateTime: 2024/9/15
 * @Description:
 **/
@EnableScheduling
@ConditionalOnProperty(prefix = "dynamic-thread-pool.management", name = "enabled", havingValue = "true")
@Configuration
@EnableConfigurationProperties(ManagementProperties.class)
public class ManagementConfig {

//    @Bean
//    public IThreadPoolDataCollectionJob nacosThreadDataCollectionJob(ManagementProperties managementProperties, DynamicThreadPoolRegistry dynamicThreadPoolRegistry, WebClient webClient) {
//        return new NacosThreadDataCollectionJob(managementProperties, dynamicThreadPoolRegistry, webClient);
//    }

    @Bean
    public IThreadPoolDataCollectionJob nacosThreadDataCollectionJob() {
        return new NacosThreadDataCollectionJob();
    }
    @Bean
    public WebClient webClient(ManagementProperties managementProperties) {
        return WebClient.builder()
                // todo 检查是否有http开头
                .baseUrl("http://"+managementProperties.getAddr()) // 设置基础 URL
                .defaultHeader("Content-Type", "application/json") // 默认请求头
                .build(); // 默认接受头
    }
}
