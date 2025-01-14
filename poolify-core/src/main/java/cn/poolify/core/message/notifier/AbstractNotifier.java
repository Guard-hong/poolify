package cn.poolify.core.message.notifier;

import lombok.extern.slf4j.Slf4j;

/**
 * @Author: HCJ
 * @DateTime: 2025/1/11
 * @Description:
 **/
@Slf4j
public abstract class AbstractNotifier implements INotifier {

    @Override
    public void send(NotifyPlatform notifyPlatform, String context) {
        try {
            doSend(notifyPlatform, context);
        } catch (Exception e) {
            log.error("notify failed. platform: {}, msg: {}", mark(), context);
        }
    }

    protected abstract void doSend(NotifyPlatform notifyPlatform, String context);
}
