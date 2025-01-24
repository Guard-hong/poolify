package cn.poolify.core.transmitter.assemble;

import cn.poolify.core.constants.DefaultAssemblerConstants;
import cn.poolify.core.enums.AlarmType;
import cn.poolify.core.executor.wrapper.ExecutorWrapper;
import org.apache.commons.lang.StringUtils;

/**
 * @Author: HCJ
 * @DateTime: 2025/1/21
 * @Description:
 **/
public class DefaultAssembler extends AbstractAssembler {
    @Override
    protected String addAlarmSuffix(ExecutorWrapper executor, AlarmType type) {
        String suffix = StringUtils.EMPTY;
        if (AlarmType.QUEUE_TIMEOUT.equals(type)) {
            suffix = "queue timeout,queue time: (" + executor.getQueueTimeout() + "ms)";
        } else if (AlarmType.RUN_TIMEOUT.equals(type)) {
            suffix = "run timeout,run time: (" + executor.getRunTimeout() + "ms)";
        }
        return suffix;
    }

    @Override
    protected String getAlarmTemplate() {
        return DefaultAssemblerConstants.ALARM_TEMPLATE;
    }

    @Override
    protected String getNoticeTemplate() {
        return DefaultAssemblerConstants.NOTICE_TEMPLATE;
    }
}
