package cn.poolify.core.transmitter.notifier;

import cn.poolify.core.entity.NotifyPlatform;

/**
 * @Author: HCJ
 * @DateTime: 2025/1/14
 * @Description:
 **/
public class DingNotifier extends AbstractNotifier{
    private static final String mark = "ding";
    @Override
    protected void doSend(NotifyPlatform notifyPlatform, String context) {

    }

    @Override
    public String mark() {
        return mark;
    }
}
