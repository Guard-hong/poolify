package cn.poolify.config.properties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

/**
 * @Author: HCJ
 * @DateTime: 2024/9/14
 * @Description:
 **/
@Data
public class NacosRegistryProperties {

    private String host = "localhost";

    private Integer port = 8848;

    private String groupId = "DEFAULT_GROUP";

    @Value("${spring.name}")
    private String dataId;

}
