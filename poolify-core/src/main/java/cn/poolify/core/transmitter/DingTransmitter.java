package cn.poolify.core.transmitter;

import cn.poolify.core.transmitter.notifier.DingNotifier;

/**
 * @Author: HCJ
 * @DateTime: 2025/1/14
 * @Description:
 **/
public class DingTransmitter extends AbstractTransmitter {
    private static final String mark = "ding";
    public DingTransmitter() {
        super(new DingNotifier());
    }

    @Override
    public String mark() {
        return mark;
    }
}
