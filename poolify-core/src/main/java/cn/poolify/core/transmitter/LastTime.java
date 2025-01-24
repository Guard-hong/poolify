package cn.poolify.core.transmitter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @Author: HCJ
 * @DateTime: 2025/1/24
 * @Description:
 **/
public class LastTime {
    private String lastAlarmTime;
    private String lastNotifyTime;
    public void reset() {
        lastAlarmTime = now();
    }
    private static String now(){
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
    }
}
