package cn.poolify.core.transmitter.notifier;

import lombok.Data;

/**
 * @Author: HCJ
 * @DateTime: 2025/1/11
 * @Description:
 **/
@Data
public class NotifyPlatform {
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
