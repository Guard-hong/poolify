package cn.poolify.admin.trigger.http.api.dto.thread;

import lombok.Data;

/**
 * @Author: HCJ
 * @DateTime: 2024/9/22
 * @Description:
 **/
@Data
public class ThreadPoolRuntimeStatusLineDTO {

    private String applicationName;

    private String threadPoolName;
}
