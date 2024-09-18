package cn.poolify.core.config.properties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Author: HCJ
 * @DateTime: 2024/9/15
 * @Description:
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties("poolify.management")
public class ManagementProperties {

    private String addr;

    private String collectionCron;

}
