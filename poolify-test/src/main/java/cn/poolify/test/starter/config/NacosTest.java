package cn.poolify.test.starter.config;

import cn.poolify.test.starter.model.ThreadPoolValObj;
import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.exception.NacosException;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * @Author: HCJ
 * @DateTime: 2024/9/13
 * @Description:
 **/
@Configuration
public class NacosTest {

    @Resource
    private ConfigService configService;
    @PostConstruct
    public void registerThreadPoolConfig() {
        try {
            // 动态获取线程池参数
            int corePoolSize = 5;
            int maxPoolSize = 11;
            long keepAliveTime = 5L;

            // 构造 Nacos 配置
            String threadPoolConfig = JSON.toJSONString(new ThreadPoolValObj());

            // 将配置发布到 Nacos
            boolean flag = configService.publishConfig(
                    "dynamic-threadpool-config-tset", // Data ID
                    "DEFAULT_GROUP", // Group
                    threadPoolConfig
            );
            System.out.println("发布： "+flag);
        } catch (NacosException e) {
        }
    }
}
