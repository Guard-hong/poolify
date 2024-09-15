package cn.poolify.admin.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Author: HCJ
 * @DateTime: 2024/9/15
 * @Description:
 **/
@Data
@ConfigurationProperties("dynamic-thread-pool.web")
public class ThreadPoolWebConfigProperties {
    /**
     * WEB根路径
     */
    private String contextPath = "";
}
