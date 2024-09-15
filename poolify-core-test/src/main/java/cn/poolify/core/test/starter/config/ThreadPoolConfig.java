package cn.poolify.core.test.starter.config;

import cn.poolify.core.aop.DynamicThreadPool;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.*;

/**
 * @Author: HCJ
 * @DateTime: 2024/9/14
 * @Description:
 **/
@Configuration
public class ThreadPoolConfig {
    @Bean
    @DynamicThreadPool
    public ThreadPoolExecutor threadPoolExecutor01() {
        return new ThreadPoolExecutor(
                5,5,5,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(3),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy()
        );
    }

    @Bean
    @DynamicThreadPool
    public ThreadPoolExecutor threadPoolExecutor02() {
        return new ThreadPoolExecutor(
                15,15,15,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(3),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy()
        );
    }

    @Bean
//    @DynamicThreadPool
    public ThreadPoolExecutor threadPoolExecutor03() {
        return new ThreadPoolExecutor(
                5,5,5,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(3),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy()
        );
    }
}
