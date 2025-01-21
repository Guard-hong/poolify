package cn.poolify.core.transmitter;

import cn.poolify.core.transmitter.notifier.DingNotifier;

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
