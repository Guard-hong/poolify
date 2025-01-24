package cn.poolify.core.transmitter.assemble;

import cn.poolify.core.enums.AlarmType;
import cn.poolify.core.executor.wrapper.ExecutorWrapper;
import cn.poolify.core.properties.entity.DtpExecutorProps;
import cn.poolify.core.utils.CommonUtil;

import java.util.Set;

/**
 * @Author: HCJ
 * @DateTime: 2025/1/24
 * @Description:
 **/
public abstract class AbstractAssembler implements IAssembler {
    @Override
    public String assembleAlarm(ExecutorWrapper executor, AlarmType type) {
        String content = String.format(
                getAlarmTemplate(),
                CommonUtil.getInstance().getServiceName(),
                executor.getThreadPoolName(),
                executor.getCorePoolSize(),
                executor.getMaximumPoolSize(),
                executor.getPoolSize(),
                executor.getActiveCount(),
                executor.getLargestPoolSize(),
                executor.getTaskCount(),
                executor.getCompletedTaskCount(),
                executor.getQueue().getClass(),
                executor.getQueue().size(),
                executor.getQueue().remainingCapacity(),
                executor.getRejectedExecutionHandler().getClass(),
                executor.getRejectCount(),
                executor.getRunTimeoutCount(),
                executor.getQueueTimeoutCount()
        );
        content += addAlarmSuffix(executor, type);
        return content;
    }

    @Override
    public String assembleNotice(DtpExecutorProps newProps, DtpExecutorProps oldProps, Set<String> diffs) {
        String content = String.format(
                getNoticeTemplate(),
                CommonUtil.getInstance().getServiceName(),
                newProps.getThreadPoolName(),
                newProps.getCorePoolSize(), oldProps.getCorePoolSize(),
                newProps.getMaximumPoolSize(), oldProps.getMaximumPoolSize(),
                newProps.isAllowCoreThreadTimeOut(), oldProps.isAllowCoreThreadTimeOut(),
                newProps.getKeepAliveTime(), oldProps.getKeepAliveTime(),
                newProps.getRunTimeout(), oldProps.getRunTimeout(),
                newProps.getQueueTimeout(), oldProps.getQueueTimeout()
        );
        return content;
    }

    /**
     * 添加后缀 -- 超时时间
     *
     * @param type
     * @return
     */
    protected abstract String addAlarmSuffix(ExecutorWrapper executor, AlarmType type);

    protected abstract String getAlarmTemplate();

    protected abstract String getNoticeTemplate();


}
