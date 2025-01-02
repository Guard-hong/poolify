package cn.poolify.core.properties.entity;

import lombok.Data;

import java.util.concurrent.TimeUnit;

/**
 * @Author: HCJ
 * @DateTime: 2024/12/30
 * @Description: 线程池参数
 **/
@Data
public class TpExecutorProps {
    /** 核心线程数 */
    private int corePoolSize;
    /** 最大线程数 */
    private int maximumPoolSize;
    /** 非核心线程存活时间 */
    private long keepAliveTime;
    /** 存活时间 */
    private TimeUnit unit = TimeUnit.MICROSECONDS;
    /** 核心线程是否过期 */
    private boolean allowCoreThreadTimeOut = false;

    public boolean coreParamIsInValid() {
        return this.getCorePoolSize() < 0
                || this.getMaximumPoolSize() <= 0
                || this.getMaximumPoolSize() < this.getCorePoolSize()
                || this.getKeepAliveTime() < 0;
    }
}
