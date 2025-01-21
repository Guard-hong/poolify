package cn.poolify.core.message;

import cn.poolify.core.executor.wrapper.ExecutorWrapper;
import cn.poolify.core.message.assemble.IAssembler;
import cn.poolify.core.message.notifier.NotifyPlatform;

/**
 * @Author: HCJ
 * @DateTime: 2025/1/14
 * @Description:
 **/
public interface ITransmitter {

    void sendNoticeMsg(NotifyPlatform platform, ExecutorWrapper executor);


    void sendAlarmMsg(NotifyPlatform platform, ExecutorWrapper executor);

}
