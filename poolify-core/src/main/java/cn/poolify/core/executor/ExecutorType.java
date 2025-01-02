package cn.poolify.core.executor;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author: HCJ
 * @DateTime: 2024/12/30
 * @Description:
 **/
@Getter
@AllArgsConstructor
public enum ExecutorType {
    DEFAULT("default", DtpExecutor.class);

    private final String name;

    private final Class<?> clazz;

    public static Class<?> getClass(String name) {
        for (ExecutorType type : ExecutorType.values()) {
            if (type.name.equals(name)) {
                return type.getClazz();
            }
        }
        return DEFAULT.getClazz();
    }
}
