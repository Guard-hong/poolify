package cn.poolify.admin.persistent.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 存储线程池的配置信息表
 * @TableName thread_pool_config
 */
@TableName(value ="thread_pool_config")
@Data
public class ThreadPoolConfig implements Serializable {
    /**
     * 唯一标识符，主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 线程池的唯一名称，用于区分不同的线程池
     */
    private String poolName;

    /**
     * 核心线程数
     */
    private Integer corePoolSize;

    /**
     * 最大线程数
     */
    private Integer maxPoolSize;

    /**
     * 队列类型
     */
    private String queueType;

    /**
     * 任务队列容量
     */
    private Integer queueCapacity;

    /**
     * 线程的空闲保持时间
     */
    private Integer keepAliveTime;

    /**
     * 线程的空闲保持时间单位 --枚举
     */
    private Integer keepAliveTimeUnit;

    /**
     * 任务拒绝策略，如：Abort、CallerRuns、Discard、DiscardOldest
     */
    private String rejectedPolicy;

    /**
     * 线程池配置创建时间
     */
    private Date createTime;

    /**
     * 线程池配置最近更新时间
     */
    private Date updateTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}