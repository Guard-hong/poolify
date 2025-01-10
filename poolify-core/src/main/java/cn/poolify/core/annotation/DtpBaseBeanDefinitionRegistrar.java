package cn.poolify.core.annotation;

import cn.poolify.core.executor.factory.DefaultThreadFactory;
import cn.poolify.core.manager.ContextManagerHelper;
import cn.poolify.core.timer.HashedWheelTimer;
import cn.poolify.core.utils.BeanRegistrationUtil;
import cn.poolify.core.utils.CollectionUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Author: HCJ
 * @DateTime: 2024/12/23
 * @Description: 加载依赖bean
 **/
public class DtpBaseBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {

    private static final String CONTEXT_MANAGER_HELPER = "ContextManagerHelper";

    private static final String HASHED_WHEEL_TIMER = "dtpHashedWheelTimer";

    private static final String DTP_POST_PROCESSOR = "dtpPostProcessor";
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        // TODO: spi加载配置中心

        // 注册HashedWheelTimer
        registerHashedWheelTimer(registry);

        BeanRegistrationUtil.registerIfAbsent(registry, CONTEXT_MANAGER_HELPER, ContextManagerHelper.class);

        // 在 ExecutorMonitor 的执行方法中，contextManagerHelper 和 HashedWheelTimer 是必需的，所以必须先注册它们
        BeanRegistrationUtil.registerIfAbsent(registry, DTP_POST_PROCESSOR, DynamicThreadPoolProcessor.class,
                null, CollectionUtils.newList(new String[]{CONTEXT_MANAGER_HELPER, HASHED_WHEEL_TIMER}));
    }

    private void registerHashedWheelTimer(BeanDefinitionRegistry registry) {
        Object[] constructorArgs = new Object[] {
                new DefaultThreadFactory(),
                10,
                TimeUnit.MILLISECONDS
        };
        BeanRegistrationUtil.registerIfAbsent(registry, HASHED_WHEEL_TIMER, HashedWheelTimer.class, constructorArgs);
    }
}
