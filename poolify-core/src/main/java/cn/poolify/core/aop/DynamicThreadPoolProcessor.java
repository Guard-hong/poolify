package cn.poolify.core.aop;

import cn.poolify.core.registry.DynamicThreadPoolRegistry;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Author: HCJ
 * @DateTime: 2024/9/14
 * @Description: 获取动态线程池
 **/
@Slf4j
@Component
public class DynamicThreadPoolProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessAfterInitialization(Object bean, @NotNull String beanName) throws BeansException {
        // 检查类或方法是否带有 @DynamicThreadPool 注解
        if (bean.getClass().isAnnotationPresent(DynamicThreadPool.class)) {
            // 如果是 ThreadPoolExecutor 类型，才处理
            if (bean instanceof ThreadPoolExecutor) {
                DynamicThreadPoolRegistry.register(beanName, (ThreadPoolExecutor) bean);
            } else{
                log.error("DynamicThreadPool 注解应用在非ThreadPoolExecutor上");
            }
        }
        return bean;
    }

}
