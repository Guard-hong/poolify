package cn.poolify.core.refresher;

import cn.poolify.core.executor.DtpRegistry;
import cn.poolify.core.properties.DtpProperties;
import cn.poolify.core.utils.BinderUtils;
import cn.poolify.core.utils.CollectionUtils;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;

import java.util.Set;
import java.util.stream.Collectors;

import static cn.poolify.core.constants.DtpConstants.DTP_EXECUTOR_PROP;

/**
 * @Author: HCJ
 * @DateTime: 2024/12/25
 * @Description:
 **/
public abstract class AbstractRefresher implements IRefresher, EnvironmentAware {
    private Environment environment;
    private final DtpProperties dtpProperties;

    public AbstractRefresher(DtpProperties dtpProperties) {
        this.dtpProperties = dtpProperties;
    }

    @Override
    public void refresh() {
        doBind();
        doRefresh();
        doNotify();
    }

    private void doNotify() {

    }

    private void doRefresh() {
        DtpRegistry.refresh(dtpProperties);
    }

    protected void doBind() {
        BinderUtils.bindDtpProperties(environment, dtpProperties);
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    protected boolean needRefresh(Set<String> changedKeys) {
        if (CollectionUtils.isEmpty(changedKeys)) {
            return false;
        }
        changedKeys = changedKeys.stream()
                .filter(str -> str.startsWith(DTP_EXECUTOR_PROP))
                .collect(Collectors.toSet());
        return CollectionUtils.isNotEmpty(changedKeys);
    }

}
