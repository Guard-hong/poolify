package cn.poolify.core.transmitter;

import cn.poolify.core.enums.AlarmType;
import cn.poolify.core.executor.wrapper.ExecutorWrapper;
import cn.poolify.core.properties.entity.DtpExecutorProps;
import cn.poolify.core.entity.NotifyPlatform;

import java.util.Set;

/**
 * @Author: HCJ
 * @DateTime: 2025/1/14
 * @Description:
 **/
public interface ITransmitter {

    String mark();

    void sendNoticeMsg(NotifyPlatform platform, DtpExecutorProps newProps, DtpExecutorProps oldProps, Set<String> diffs);

    void sendAlarmMsg(NotifyPlatform platform, ExecutorWrapper executor, AlarmType type);

}
