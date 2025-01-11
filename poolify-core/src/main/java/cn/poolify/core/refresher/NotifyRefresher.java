package cn.poolify.core.refresher;

import cn.poolify.core.properties.DtpProperties;

/**
 * @Author: HCJ
 * @DateTime: 2025/1/10
 * @Description:
 **/
public class NotifyRefresher extends AbstractRefresher {
    private final static String REFRESHER = "notify";

    public NotifyRefresher(DtpProperties dtpProperties) {
        super(dtpProperties);
    }

    /**
     * TODO: 添加通知
     */
    @Override
    protected void doNotify() {

    }

    @Override
    public String mark() {
        return null;
    }
}
