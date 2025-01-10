package cn.poolify.core.executor;

import cn.poolify.core.executor.wrapper.ExecutorWrapper;
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
        DtpExecutorProps dtpExecutorProps = new DtpExecutorProps();
        dtpExecutorProps.setThreadPoolName(executor.getThreadPoolName());
        dtpExecutorProps.setQueueTimeout(executor.getQueueTimeout());
        dtpExecutorProps.setRunTimeout(executor.getRunTimeout());
        dtpExecutorProps.setKeepAliveTime(executor.getKeepAliveTime(TimeUnit.SECONDS));
        dtpExecutorProps.setAllowCoreThreadTimeOut(false);
        dtpExecutorProps.setCorePoolSize(executor.getCorePoolSize());
        dtpExecutorProps.setMaximumPoolSize(executor.getMaximumPoolSize());
        dtpExecutorProps.setAllowCoreThreadTimeOut(executor.allowsCoreThreadTimeOut());

        return dtpExecutorProps;
    }
}
