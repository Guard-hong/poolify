package cn.poolify.core.properties.entity;

import lombok.Builder;
import lombok.Data;

import java.util.concurrent.TimeUnit;

/**
 * @Author: HCJ
 * @DateTime: 2024/12/25
 * @Description: 动态线程池属性
 * TODO: 配置默认值
 **/
@Data
public class DtpExecutorProps extends TpExecutorProps{
    private String threadPoolName;
    private String executorType;
    private long queueTimeout;
    private long runTimeout;

}
