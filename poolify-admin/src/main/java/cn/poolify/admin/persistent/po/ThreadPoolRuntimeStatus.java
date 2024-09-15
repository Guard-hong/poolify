package cn.poolify.admin.persistent.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 定期采集并记录线程池的运行时状态，用于监控和分析线程池的表现
 * @TableName thread_pool_runtime_status
 */
@TableName(value ="thread_pool_runtime_status")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ThreadPoolRuntimeStatus implements Serializable {
    /**
     * 唯一标识符，主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 应用名称，用于标识数据属于哪个应用
     */
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

    /**
     * 记录状态的时间戳
     */
    private Date timestamp;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}