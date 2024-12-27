package cn.poolify.core.executor;

import cn.poolify.core.properties.entity.DtpExecutorProps;

import java.util.concurrent.TimeUnit;

/**
 * @Author: HCJ
 * @DateTime: 2024/12/27
 * @Description:
 **/
public class ExecutorConverter {
    private ExecutorConverter() {
    }

    ;

    public static DtpExecutorProps toDtpExecutorProps(ExecutorWrapper executor) {
        return DtpExecutorProps.builder()
                .threadPoolName(executor.toString())
                .corePoolSize(executor.getCorePoolSize())
                .maximumPoolSize(executor.getMaximumPoolSize())
                .keepAliveTime(executor.getKeepAliveTime(TimeUnit.SECONDS))
                .allowCoreThreadTimeOut(executor.allowsCoreThreadTimeOut())
                .queueTimeout(executor.getQueueTimeout())
                .runTimeout(executor.getRunTimeout())
                .build();
    }
}
