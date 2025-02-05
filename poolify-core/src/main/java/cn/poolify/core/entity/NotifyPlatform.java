package cn.poolify.core.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author: HCJ
 * @DateTime: 2025/1/11
 * @Description: 通知平台信息
 * 重写 equals和hashCode 进行去重过滤
 **/
@EqualsAndHashCode
@Data
public class NotifyPlatform {
    /**
     * platform
     */
    private String platform;
    /**
     * url
     */
    private String url;
    /**
     * token
     */
    private String token;
    /**
     * receivers, split by ,
     */
    private String receivers;


}
