package cn.poolify.core.aop;

import cn.poolify.core.config.properties.DynamicThreadProperties;
import cn.poolify.core.registry.DynamicThreadPoolRegistry;
import cn.poolify.core.registry.IRegistry;
import cn.poolify.core.registry.model.entity.ThreadPoolConfigEntity;
import cn.poolify.core.registry.model.entity.RegistryThreadPool;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Author: HCJ
 * @DateTime: 2024/9/14
 * @Description: 获取动态线程池
 **/
@Slf4j
@Component
public class DynamicThreadPoolProcessor implements BeanPostProcessor, ApplicationContextAware {
    private static ApplicationContext CONTEXT;

    @Resource
    private Map<String, IRegistry> registryMap;

    @Resource
    private DynamicThreadProperties dynamicThreadProperties;

    @Resource
    private DynamicThreadPoolRegistry dynamicThreadPoolRegistry;


    @Override
    public Object postProcessAfterInitialization(Object bean, @NotNull String beanName) throws BeansException {
        if (bean instanceof ThreadPoolExecutor && CONTEXT.findAnnotationOnBean(beanName, DynamicThreadPool.class)!=null){
            dynamicThreadPoolRegistry.register(beanName, (ThreadPoolExecutor) bean);
            IRegistry registry = registryMap.get(dynamicThreadProperties.getType());
            try {
                String applicationName = dynamicThreadProperties.getApplicationName();
                RegistryThreadPool registryThreadPool = registry.queryThreadPoolConfig(applicationName, beanName);
                // 已经注册过
                if(registryThreadPool != null){
                    // 更新线程池
                    dynamicThreadPoolRegistry.updateThreadPoolParameter(beanName, registryThreadPool);
                }else{
                    // 发布注册信息
                    registry.reportThreadPool(ThreadPoolConfigEntity.buildThreadPoolConfigEntity(applicationName,beanName,(ThreadPoolExecutor)bean));
                }
            } catch (Exception e) {
                log.error("bean: {} registry fail!",beanName);
                throw new RuntimeException(e);
            }
        }


        return bean;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        DynamicThreadPoolProcessor.CONTEXT = applicationContext;
    }
}
