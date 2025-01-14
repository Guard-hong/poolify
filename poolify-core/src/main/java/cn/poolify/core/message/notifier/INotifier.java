package cn.poolify.core.message.notifier;

/**
 * @Author: HCJ
 * @DateTime: 2025/1/11
 * @Description:
 **/
public interface INotifier {

    String mark();
    void send(NotifyPlatform notifyPlatform,String context);
}
