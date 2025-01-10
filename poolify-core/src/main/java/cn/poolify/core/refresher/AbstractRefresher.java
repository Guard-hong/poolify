package cn.poolify.core.refresher;

import cn.poolify.core.executor.DtpRegistry;
import cn.poolify.core.properties.DtpProperties;
import cn.poolify.core.utils.BinderUtils;
import org.springframework.cloud.context.environment.EnvironmentChangeEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.event.SmartApplicationListener;
import org.springframework.core.env.Environment;
import org.springframework.util.CollectionUtils;

import java.util.Set;
import java.util.stream.Collectors;

import static cn.poolify.core.constants.DtpConstants.DTP_EXECUTOR_PROP;

/**
 * @Author: HCJ
 * @DateTime: 2024/12/25
 * @Description:
 **/
public abstract class AbstractRefresher implements IRefresher, SmartApplicationListener, EnvironmentAware {
    private Environment environment;
    private final DtpProperties dtpProperties;

    public AbstractRefresher(DtpProperties dtpProperties) {
        this.dtpProperties = dtpProperties;
    }

    @Override
    public void refresh() {
        doBind();
        doRefresh();
    }

    private void doRefresh() {
        DtpRegistry.refresh(dtpProperties);
        // TODO: 通知
    }

    protected void doBind() {
        BinderUtils.bindDtpProperties(environment, dtpProperties);
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
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

    protected boolean needRefresh(Set<String> changedKeys) {
        if (CollectionUtils.isEmpty(changedKeys)) {
            return false;
        }
        changedKeys = changedKeys.stream()
                .filter(str -> str.startsWith(DTP_EXECUTOR_PROP))
                .collect(Collectors.toSet());
        return !CollectionUtils.isEmpty(changedKeys);
    }

}
