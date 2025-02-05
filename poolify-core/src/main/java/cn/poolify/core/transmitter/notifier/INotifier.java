package cn.poolify.core.transmitter.notifier;

import cn.poolify.core.entity.NotifyPlatform;

/**
 * @Author: HCJ
 * @DateTime: 2025/1/11
 * @Description:
 **/
public interface INotifier {

    String mark();
    void send(NotifyPlatform notifyPlatform, String context);
}
