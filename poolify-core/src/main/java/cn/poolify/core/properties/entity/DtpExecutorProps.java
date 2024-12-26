package cn.poolify.core.properties.entity;

import lombok.Data;

import java.util.concurrent.TimeUnit;

/**
 * @Author: HCJ
 * @DateTime: 2024/12/25
 * @Description: 动态线程池属性
 * TODO: 配置默认值
 **/
@Data
public class DtpExecutorProps {
    private long keepAliveTime;
    private boolean allowCoreThreadTimeOut;
    private long corePoolSize;
    private long maximumPoolSize;
    private TimeUnit unit;
    private long queueTimeout;
    private long runTimeout;
}
