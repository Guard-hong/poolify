package cn.poolify.admin.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * @Author: HCJ
 * @DateTime: 2024/9/22
 * @Description:
 **/

@Data
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties(prefix = "poolify.alarm")
public class AlarmProperties {

    private List<String> usePlatform;

    private String token;
}
