package cn.poolify.core.refresher;

import cn.poolify.core.properties.DtpProperties;

/**
 * @Author: HCJ
 * @DateTime: 2025/1/10
 * @Description:
 **/
public class DefaultRefresher extends AbstractRefresher {
    private final static String REFRESHER = "default";

    public DefaultRefresher(DtpProperties dtpProperties) {
        super(dtpProperties);
    }

    /**
     * default not notify
     */
    @Override
    protected void doNotify() {

    }

    @Override
    public String mark() {
        return REFRESHER;
    }
}
