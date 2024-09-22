package cn.poolify.admin.trigger.http.api.dto.thread;

import lombok.Data;

/**
 * @Author: HCJ
 * @DateTime: 2024/9/19
 * @Description: 查询
 **/
@Data
public class QueryThreadPoolConfigDTO {

    /** 应用名 */
    private String applicationName;
    /**
     * 线程池名称，用于标识数据属于哪个线程池
     */
    private String threadPoolName;


    private Integer size;

    private long current;

}
