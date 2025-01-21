package cn.poolify.core.config;

import cn.poolify.core.properties.DtpProperties;
import cn.poolify.core.refresher.DefaultRefresher;
import cn.poolify.core.refresher.IRefresher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: HCJ
 * @DateTime: 2025/1/11
 * @Description:
 **/
@Configuration
public class DtpBaseBeanConfiguration {

    @Bean
    public DtpProperties dtpProperties() {
        return DtpProperties.getInstance();
    }

    @Bean
    public IRefresher defaultRefresher(DtpProperties dtpProperties){
        return new DefaultRefresher(dtpProperties);
    }
}
