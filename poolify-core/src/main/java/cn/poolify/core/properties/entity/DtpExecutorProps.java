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
    private String threadPoolName;
    private long keepAliveTime;
    private boolean allowCoreThreadTimeOut;
    private int corePoolSize;
    private int maximumPoolSize;
    private TimeUnit unit;
    private long queueTimeout;
    private long runTimeout;

    public boolean coreParamIsInValid() {
        return this.getCorePoolSize() < 0
                || this.getMaximumPoolSize() <= 0
                || this.getMaximumPoolSize() < this.getCorePoolSize()
                || this.getKeepAliveTime() < 0;
    }
}
