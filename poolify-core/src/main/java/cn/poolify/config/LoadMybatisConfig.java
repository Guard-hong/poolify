package cn.poolify.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: HCJ
 * @DateTime: 2024/9/13
 * @Description:
 **/
@MapperScan("cn.poolify.persistent.mapper")
@Configuration
public class LoadMybatisConfig {
}
