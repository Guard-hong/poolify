package cn.poolify.core.refresher;

import cn.poolify.core.properties.DtpProperties;
import org.springframework.cloud.context.environment.EnvironmentChangeEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.SmartApplicationListener;

/**
 * @Author: HCJ
 * @DateTime: 2025/1/10
 * @Description:
 **/
public class DefaultRefresher extends AbstractRefresher implements SmartApplicationListener {
    private final static String REFRESHER = "default";

    public DefaultRefresher(DtpProperties dtpProperties) {
        super(dtpProperties);
    }

    @Override
    public String mark() {
        return REFRESHER;
    }

    @Override
    public boolean supportsEventType(Class<? extends ApplicationEvent> eventType) {
        return EnvironmentChangeEvent.class.isAssignableFrom(eventType);
    }

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if (needRefresh(((EnvironmentChangeEvent) event).getKeys())) {
            refresh();
        }
    }
}
