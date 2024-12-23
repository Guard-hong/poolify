package cn.poolify.core.aop;

import cn.poolify.core.config.properties.DynamicThreadProperties;
import cn.poolify.core.feign.ManagementFeign;
import cn.poolify.core.manager.ContextManagerHelper;
import cn.poolify.core.registry.DtpRegistry;
import cn.poolify.core.registry.IRegistry;
import cn.poolify.core.registry.model.entity.ThreadPoolConfigEntity;
import cn.poolify.core.registry.model.entity.RegistryThreadPool;
import cn.poolify.core.registry.model.val.RegistryThreadPoolConfigVO;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.net.InetAddress;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Author: HCJ
 * @DateTime: 2024/9/14
 * @Description: 获取动态线程池
 **/
@Slf4j
@Component
public class DynamicThreadPoolProcessor implements BeanPostProcessor {
    @Resource
    private Map<String, IRegistry> registryMap;

    @Resource
    private DynamicThreadProperties dynamicThreadProperties;

    @Resource
    private DtpRegistry dtpRegistry;

    @Autowired(required = false)
    private ManagementFeign managementFeign;

    private static final String HTTP = "http://";

    private static final String COLON = ":";

    private static final String ENV_PORT = "server.port";


    @Override
    public Object postProcessAfterInitialization(Object bean, @NotNull String beanName) throws BeansException {
        if (bean instanceof ThreadPoolExecutor && ContextManagerHelper.getContext().findAnnotationOnBean(beanName, DynamicThreadPool.class) != null) {
            ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) bean;
            dtpRegistry.register(beanName, threadPoolExecutor);
            IRegistry registry = registryMap.get(dynamicThreadProperties.getType());
            try {
                String applicationName = dynamicThreadProperties.getApplicationName();
                RegistryThreadPool registryThreadPool = registry.queryThreadPoolConfig(applicationName, beanName);
                // 已经注册过
                if (registryThreadPool != null) {
                    // 更新线程池
                    dtpRegistry.updateThreadPoolParameter(beanName, registryThreadPool);
                } else {
                    // 发布注册信息
                    registry.reportThreadPool(ThreadPoolConfigEntity.buildThreadPoolConfigEntity(applicationName, beanName, threadPoolExecutor));
                    if (managementFeign != null) {
                        managementFeign.registryThreadPool(RegistryThreadPoolConfigVO.builder()
                                .registryType(dynamicThreadProperties.getType())
                                .addr(HTTP + InetAddress.getLocalHost().getHostAddress() + COLON + ContextManagerHelper.getContext().getEnvironment().getProperty(ENV_PORT))
                                .applicationName(applicationName)
                                .threadPoolName(beanName)
                                .corePoolSize(threadPoolExecutor.getCorePoolSize())
                                .maximumPoolSize(threadPoolExecutor.getMaximumPoolSize())
                                .queueType(threadPoolExecutor.getQueue().getClass().toString())
//                                        .queueCapacity(threadPoolExecutor.)
//                                        .keepAliveTime(threadPoolExecutor)
//                                        .keepAliveTimeUnit()
//                                        .rejectedPolicy()
                                .build());
                    }
                }
            } catch (Exception e) {
                log.error("bean: {} registry fail!", beanName);
                throw new RuntimeException(e);
            }
        }
        return bean;
    }

}
