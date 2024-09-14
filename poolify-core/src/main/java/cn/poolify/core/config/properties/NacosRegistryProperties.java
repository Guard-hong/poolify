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
@ConfigurationProperties("dynamic-thread-pool.registry.nacos")
public class NacosRegistryProperties {

    private String host = "localhost";

    private Integer port = 8848;

    @Value("${spring.name:''}")
    private String dataId;

    private String groupId = "DEFAULT_GROUP";


}
