package cn.poolify.core.message;

import cn.poolify.core.message.notifier.DingNotifier;

/**
 * @Author: HCJ
 * @DateTime: 2025/1/14
 * @Description:
 **/
public class DingTransmitter extends AbstractTransmitter {
    public DingTransmitter() {
        super(new DingNotifier());
    }
}
