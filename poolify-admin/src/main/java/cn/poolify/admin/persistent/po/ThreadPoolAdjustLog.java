package cn.poolify.admin.persistent.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 记录每次对线程池进行的动态调整操作，包括线程池大小的调整及原因
 * @TableName thread_pool_adjust_log
 */
@TableName(value ="thread_pool_adjust_log")
@Data
public class ThreadPoolAdjustLog implements Serializable {
    /**
     * 唯一标识符，主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 被调整的线程池名称
     */
    private String poolName;

    /**
     * 调整前的核心线程数
     */
    private Integer beforeCorePoolSize;

    /**
     * 调整后的核心线程数
     */
    private Integer afterCorePoolSize;

    /**
     * 调整前的最大线程数
     */
    private Integer beforeMaxPoolSize;

    /**
     * 调整后的最大线程数
     */
    private Integer afterMaxPoolSize;

    /**
     * 调整的原因或触发条件
     */
    private String adjustmentReason;

    /**
     * 调整记录的时间
     */
    private Date createTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}