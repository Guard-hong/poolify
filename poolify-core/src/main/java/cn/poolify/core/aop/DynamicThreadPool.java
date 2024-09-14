package cn.poolify.core.aop;

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
}
