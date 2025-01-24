package cn.poolify.core.transmitter.assemble;

import cn.poolify.core.enums.AlarmType;
import cn.poolify.core.executor.wrapper.ExecutorWrapper;
import cn.poolify.core.properties.entity.DtpExecutorProps;

import java.util.Set;

/**
 * @Author: HCJ
 * @DateTime: 2025/1/13
 * @Description:
 * 提供一个默认实现
 **/
public interface IAssembler {

    String assembleAlarm(ExecutorWrapper executor, AlarmType type);
    String assembleNotice(DtpExecutorProps newProps, DtpExecutorProps oldProps, Set<String> diffs);
}
