package cn.poolify.core.test.starter.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author: HCJ
 * @DateTime: 2024/9/14
 * @Description:
 **/
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ThreadPoolEntity implements Serializable {
    /** 线程池名称 */
    private String threadthreadPoolName;
    /** 核心线程数 */
    private Integer corePoolSize;
    /** 最大线程数 */
    private Integer maximumPoolSize;
//    /** 当前活跃线程数 */
//    private Integer activeThreadCount;
//    /** 池中线程数 */
//    private int poolSize;
//    /** 队列类型 */
//    private String queueType;
//    /** 队列中任务数 */
//    private Integer queueSize;
//    /** 队列剩余的任务数 */
//    private Integer remainingCapacity;
}
