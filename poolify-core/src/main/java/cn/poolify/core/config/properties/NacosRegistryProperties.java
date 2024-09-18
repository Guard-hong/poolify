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

    @Value("${poolify.registry.nacos.host:localhost}")
    private String host;

    @Value("${poolify.registry.nacos.port:8848}")
    private Integer port;

    @Value("${spring.name:DEFAULT_GROUP}")
    private String groupId;


}
