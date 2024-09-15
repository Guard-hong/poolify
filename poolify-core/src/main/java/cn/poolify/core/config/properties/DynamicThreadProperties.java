package cn.poolify.core.config.properties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
/**
 * @Author: HCJ
 * @DateTime: 2024/9/15
 * @Description:
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties("")
//@Component
public class DynamicThreadProperties {

    @Value("${dynamic-thread-pool.registry.type}")
    private String type;

    @Value("${spring.name}")
    private String applicationName;

}