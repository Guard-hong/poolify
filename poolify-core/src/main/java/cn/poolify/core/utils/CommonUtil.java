package cn.poolify.core.utils;

import cn.poolify.core.entity.ServiceInstance;
import cn.poolify.core.manager.ContextManagerHelper;

import static cn.poolify.core.constants.DtpConstants.APP_NAME_KEY;

/**
 * @Author: HCJ
 * @DateTime: 2025/1/24
 * @Description:
 **/
public class CommonUtil {
    private CommonUtil() {
    }

    private static final ServiceInstance SERVICE_INSTANCE;
    static {
        String appName = ContextManagerHelper.getEnvironmentProperty(APP_NAME_KEY);
        SERVICE_INSTANCE = new ServiceInstance(appName);
    }
    public static ServiceInstance getInstance() {
        return SERVICE_INSTANCE;
    }
}
