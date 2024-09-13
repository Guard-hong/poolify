package cn.poolify.test.starter.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Author: HCJ
 * @DateTime: 2024/9/13
 * @Description:
 **/

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NacosProperties {

//    @Value("${spring.cloud.nacos.config.server-addr}")
    private String addrServer;
}
