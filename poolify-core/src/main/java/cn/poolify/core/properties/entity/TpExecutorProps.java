package cn.poolify.core.properties.entity;

import lombok.Data;

import java.util.concurrent.TimeUnit;

/**
 * @Author: HCJ
 * @DateTime: 2024/12/30
 * @Description:
 **/
@Data
public class TpExecutorProps {
    private long keepAliveTime;
    private boolean allowCoreThreadTimeOut;
    private int corePoolSize;
    private int maximumPoolSize;
    private TimeUnit unit;

    public boolean coreParamIsInValid() {
        return this.getCorePoolSize() < 0
                || this.getMaximumPoolSize() <= 0
                || this.getMaximumPoolSize() < this.getCorePoolSize()
                || this.getKeepAliveTime() < 0;
    }
}
