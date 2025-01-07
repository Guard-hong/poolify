package cn.poolify.core.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;

import java.util.List;
import java.util.Map;

/**
 * @Author: HCJ
 * @DateTime: 2024/12/24
 * @Description:
 **/
@Slf4j
public final class BeanRegistrationUtil {

    private BeanRegistrationUtil() { }

    public static void register(BeanDefinitionRegistry registry,
                                String beanName,
                                Class<?> clazz,
                                Map<String, Object> propertyValues,
                                Object... constructorArgs) {
        register(registry, beanName, clazz, propertyValues, null, constructorArgs);
    }

    public static void register(BeanDefinitionRegistry registry,
                                String beanName,
                                Class<?> clazz,
                                Map<String, Object> propertyValues,
                                List<String> dependsOnBeanNames,
                                Object... constructorArgs) {
        if (ifPresent(registry, beanName, clazz) || registry.containsBeanDefinition(beanName)) {
            log.info("DynamicTp registrar, bean [{}] already exists and will be overwritten", beanName);
            registry.removeBeanDefinition(beanName);
        }
        doRegister(registry, beanName, clazz, propertyValues, dependsOnBeanNames, constructorArgs);
    }

    public static void registerIfAbsent(BeanDefinitionRegistry registry,
                                        String beanName,
                                        Class<?> clazz,
                                        Object... constructorArgs) {
        registerIfAbsent(registry, beanName, clazz, null, null, constructorArgs);
    }

    public static void registerIfAbsent(BeanDefinitionRegistry registry,
                                        String beanName,
                                        Class<?> clazz,
                                        Map<String, Object> propertyValues,
                                        Object... constructorArgs) {
        registerIfAbsent(registry, beanName, clazz, propertyValues, null, constructorArgs);
    }

    public static void registerIfAbsent(BeanDefinitionRegistry registry,
                                        String beanName,
                                        Class<?> clazz,
                                        Map<String, Object> propertyValues,
                                        List<String> dependsOnBeanNames,
                                        Object... constructorArgs) {
        if (!ifPresent(registry, beanName, clazz) && !registry.containsBeanDefinition(beanName)) {
            doRegister(registry, beanName, clazz, propertyValues, dependsOnBeanNames, constructorArgs);
        }
    }

    public static boolean ifPresent(BeanDefinitionRegistry registry, String beanName, Class<?> clazz) {
        String[] beanNames = getBeanNames((ListableBeanFactory) registry, clazz);
        return ArrayUtils.contains(beanNames, beanName);
    }

    public static String[] getBeanNames(ListableBeanFactory beanFactory, Class<?> clazz) {
        return beanFactory.getBeanNamesForType(clazz, true, false);
    }

    private static void doRegister(BeanDefinitionRegistry registry,
                                   String beanName,
                                   Class<?> clazz,
                                   Map<String, Object> propertyValues,
                                   List<String> dependsOnBeanNames,
                                   Object... constructorArgs) {
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(clazz);
        for (Object constructorArg : constructorArgs) {
            builder.addConstructorArgValue(constructorArg);
        }
        if (!(propertyValues == null || propertyValues.isEmpty())) {
            propertyValues.forEach(builder::addPropertyValue);
        }
        if (CollectionUtils.isNotEmpty(dependsOnBeanNames)) {
            dependsOnBeanNames.forEach(builder::addDependsOn);
        }
        registry.registerBeanDefinition(beanName, builder.getBeanDefinition());
    }
}
