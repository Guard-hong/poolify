package cn.poolify.admin.trigger.http.api.dto.thread;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @Author: HCJ
 * @DateTime: 2024/9/19
 * @Description:
 **/
@Data
public class ThreadPoolAdjustLogDTO {

    private Long threadPoolConfigId;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date beginDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;
}
