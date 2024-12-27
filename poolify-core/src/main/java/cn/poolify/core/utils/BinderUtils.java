package cn.poolify.core.utils;

import cn.poolify.core.properties.DtpProperties;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.core.ResolvableType;
import org.springframework.core.env.Environment;

import java.util.Objects;

import static cn.poolify.core.constants.DtpConstants.MAIN_PROPERTIES_PREFIX;

/**
 * @Author: HCJ
 * @DateTime: 2024/12/27
 * @Description:
 **/
public final class BinderUtils {
    private BinderUtils(){};

    public static void bindDtpProperties(Environment environment, DtpProperties dtpProperties) {
        Binder binder = Binder.get(environment);
        ResolvableType type = ResolvableType.forClass(DtpProperties.class);
        Bindable<?> target = Bindable.of(type).withExistingValue(dtpProperties);
        binder.bind(MAIN_PROPERTIES_PREFIX, target);
    }
}
