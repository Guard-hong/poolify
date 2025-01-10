package cn.poolify.core.annotation;

import cn.poolify.core.executor.factory.DefaultThreadFactory;
import cn.poolify.core.executor.wrapper.ExecutorType;
import cn.poolify.core.properties.DtpProperties;
import cn.poolify.core.properties.entity.DtpExecutorProps;
import cn.poolify.core.utils.BeanRegistrationUtil;
import cn.poolify.core.utils.BinderUtils;
import com.alibaba.nacos.shaded.com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static cn.poolify.core.constants.DtpConstants.*;

/**
 * @Author: HCJ
 * @DateTime: 2024/12/30
 * @Description: 根据配置文件创建动态线程池bean对象
 **/
@Slf4j
public class DtpBeanDefinitionRegister implements ImportBeanDefinitionRegistrar, EnvironmentAware {
    private Environment environment;
    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        DtpProperties dtpProperties = DtpProperties.getInstance();
        BinderUtils.bindDtpProperties(environment, dtpProperties);
        List<DtpExecutorProps> executors = dtpProperties.getExecutors();
        if (CollectionUtils.isEmpty(executors)) {
            log.info("DynamicTp registrar, no executors are configured.");
            return;
        }

        executors.forEach(e -> {
            Class<?> executorTypeClass = ExecutorType.getClass(e.getExecutorType());
            Map<String, Object> propertyValues = buildPropertyValues(e);
            Object[] args = buildConstructorArgs(executorTypeClass, e);
            BeanRegistrationUtil.register(registry, e.getThreadPoolName(), executorTypeClass, propertyValues, args);
        });
    }

    /**
     * TODO
     * @param executorTypeClass
     * @param props
     * @return
     */
    private Object[] buildConstructorArgs(Class<?> executorTypeClass, DtpExecutorProps props) {
        return new Object[]{
                props.getCorePoolSize(),
                props.getMaximumPoolSize(),
                props.getKeepAliveTime(),
                TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(), // TODO
                new DefaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy() // TODO
        };
    }

    /**
     * TODO
     * @param props
     * @return
     */
    private Map<String, Object> buildPropertyValues(DtpExecutorProps props) {
        Map<String, Object> propertyValues = Maps.newHashMap();
        propertyValues.put(THREAD_POOL_NAME, props.getThreadPoolName());
        propertyValues.put(RUN_TIMEOUT, props.getRunTimeout());
        propertyValues.put(QUEUE_TIMEOUT, props.getQueueTimeout());

        return propertyValues;
    }
}
