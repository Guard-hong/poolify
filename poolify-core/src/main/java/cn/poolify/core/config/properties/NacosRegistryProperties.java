package cn.poolify.core.config.properties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Author: HCJ
 * @DateTime: 2024/9/14
 * @Description:
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties("")
public class NacosRegistryProperties {

    @Value("${dynamic-thread-pool.registry.nacos.host:localhost}")
    private String host;

    @Value("${dynamic-thread-pool.registry.nacos.port:8848}")
    private Integer port;

    @Value("${spring.name}")
    private String dataId;

    @Value("${dynamic-thread-pool.registry.nacos.group-id:DEFAULT_GROUP}")
    private String groupId;


}
