package cn.poolify.core.annotation;

import cn.poolify.core.executor.ThreadPoolExecutorProxy;
import cn.poolify.core.registry.DtpRegistry;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.core.Ordered;
import org.springframework.core.PriorityOrdered;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Author: HCJ
 * @DateTime: 2024/9/14
 * @Description: 注册动态线程池
 * TODO: 考虑去掉使用注解方式来管理线程池
 **/
@Slf4j
@Component
public class DynamicThreadPoolProcessor implements BeanPostProcessor, BeanFactoryAware, PriorityOrdered {

    private DefaultListableBeanFactory factory;

    @Override
    public Object postProcessAfterInitialization(Object bean, @NotNull String beanName) throws BeansException {
        // 使用了注解和类型为ThreadPoolExecutor被管理
        if (!(bean instanceof ThreadPoolExecutor)) {
            return bean;
        }
        DynamicThreadPool dynamicThreadPool = factory.findAnnotationOnBean(beanName, DynamicThreadPool.class);
        if(dynamicThreadPool == null) {
            return bean;
        }
        String dtpAnnoValue = dynamicThreadPool.value();
        String poolName = StringUtils.isNotBlank(dtpAnnoValue) ? dtpAnnoValue : beanName;
        return doRegisterAndProxy(poolName,(ThreadPoolExecutor)bean);
    }

    private Object doRegisterAndProxy(String poolName,ThreadPoolExecutor executor) {
        // 1. 创建代理对象
        ThreadPoolExecutorProxy proxy = new ThreadPoolExecutorProxy(poolName,executor);
        DtpRegistry.register(poolName,proxy);
        return proxy;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.factory = (DefaultListableBeanFactory)beanFactory;
    }


    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}
