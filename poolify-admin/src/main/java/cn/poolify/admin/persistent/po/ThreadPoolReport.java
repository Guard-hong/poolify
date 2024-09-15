package cn.poolify.admin.persistent.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 线程池汇报时间表
 * @TableName thread_pool_report
 */
@TableName(value ="thread_pool_report")
@Data
public class ThreadPoolReport implements Serializable {
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
     * 线程池名字
     */
    private String threadPoolName;

    /**
     * 线程池id
     */
    private Long poolId;

    /**
     * 调度类型
     */
    private String scheduleType;

    /**
     * 调度配置，值含义取决于调度类型
     */
    private String scheduleConf;

    /**
     * 上次调度时间
     */
    private Long triggerLastTime;

    /**
     * 下次调度时间
     */
    private Long triggerNextTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}