package cn.poolify.core.annotation;

import java.lang.annotation.*;

/**
 * @Author: HCJ
 * @DateTime: 2024/9/14
 * @Description:
 **/
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DynamicThreadPool {

    String value() default "";

    long runTimeout() default 0L;
    long queueTimeout() default 0L;
}
