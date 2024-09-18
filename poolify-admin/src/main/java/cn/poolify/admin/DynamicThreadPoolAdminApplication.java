package cn.poolify.admin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author: HCJ
 * @DateTime: 2024/9/13
 * @Description:
 **/
@MapperScan("cn.poolify.admin.persistent.mapper")
@SpringBootApplication
public class DynamicThreadPoolAdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(DynamicThreadPoolAdminApplication.class, args);
    }
}
