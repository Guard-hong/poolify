package cn.poolify.core.registry.model.val;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @Author: HCJ
 * @DateTime: 2024/9/15
 * @Description: 收集线程池参数
 **/
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CollectionThreadPoolConfigVO {

    /** 应用名 */
    private String applicationName;
    /**
     * 线程池名称，用于标识数据属于哪个线程池
     */
    private String threadPoolName;

    /**
     * 当前线程池中正在执行任务的活跃线程数
     */
    private Integer activeThreadCount;

    /**
     * 当前线程池任务队列中等待执行的任务数
     */
    private Integer queueSize;

    /**
     * 线程池中已完成的任务总数
     */
    private Long completedTaskCount;

}
