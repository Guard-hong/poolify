package cn.poolify.core.transmitter;

import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: HCJ
 * @DateTime: 2025/1/24
 * @Description:
 **/
@Data
public class TransmitterManager {
    public static final Map<String,LastTime> LAST_TIME_MAP = new ConcurrentHashMap<>();




}
