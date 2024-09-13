package cn.poolify.test.starter.model;

import lombok.Data;

/**
 * @Author: HCJ
 * @DateTime: 2024/9/13
 * @Description:
 **/
@Data
public class ThreadPoolValObj {

    private int corePoolSize = 5;
    private int maxPoolSize = 11;
    private long keepAliveTime = 5L;
}
