package cn.poolify.core.properties.entity;

import lombok.Builder;
import lombok.Data;

import java.util.concurrent.TimeUnit;

/**
 * @Author: HCJ
 * @DateTime: 2024/12/25
 * @Description: 动态线程池属性
 **/
@Data
public class DtpExecutorProps extends TpExecutorProps{
    /** 线程池名称 -- bean name */
    private String threadPoolName;
    /** 线程池类型 -- 默认default TODO: 暂时只有default类型 */
    private String executorType = "default";
    /** 等待超时时间 */
    private long queueTimeout = 100;
    /** 运行超时时间 */
    private long runTimeout = 100;

}
