package cn.poolify.core.transmitter;

import cn.poolify.core.executor.wrapper.ExecutorWrapper;
import cn.poolify.core.transmitter.notifier.NotifyPlatform;

/**
 * @Author: HCJ
 * @DateTime: 2025/1/14
 * @Description:
 **/
public interface ITransmitter {

    void sendNoticeMsg(NotifyPlatform platform, ExecutorWrapper executor);


    void sendAlarmMsg(NotifyPlatform platform, ExecutorWrapper executor);

}
