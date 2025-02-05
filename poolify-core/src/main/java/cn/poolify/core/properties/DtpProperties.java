package cn.poolify.core.properties;

import cn.poolify.core.properties.entity.DtpExecutorProps;
import cn.poolify.core.entity.NotifyPlatform;
import lombok.Data;

import java.util.List;

/**
 * @Author: HCJ
 * @DateTime: 2024/12/25
 * @Description: 用于加载配置文件
 **/
@Data
public class DtpProperties {
    private List<DtpExecutorProps> executors;
    private List<NotifyPlatform> notifyPlatforms;

    public static DtpProperties getInstance() {
        return Holder.INSTANCE;
    }
    private static class Holder{
        private static final DtpProperties INSTANCE = new DtpProperties();
    }
}
