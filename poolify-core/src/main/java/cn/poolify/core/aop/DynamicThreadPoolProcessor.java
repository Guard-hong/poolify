package cn.poolify.core.aop;

import cn.poolify.core.monitor.ExecutorMonitor;
import cn.poolify.core.proxy.ThreadPoolExecutorProxy;
import cn.poolify.core.registry.DtpRegistry;
import cn.poolify.core.wrapper.ExecutorWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Author: HCJ
 * @DateTime: 2024/9/14
 * @Description: 获取动态线程池
 **/
@Slf4j
@Component
public class DynamicThreadPoolProcessor implements BeanPostProcessor, BeanFactoryAware {

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

        DtpRegistry.register(poolName,executor);
        return new ThreadPoolExecutorProxy(executor);
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.factory = (DefaultListableBeanFactory)beanFactory;
    }
}
