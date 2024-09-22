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
 * 记录每次对线程池进行的动态调整操作，包括线程池大小的调整及原因
 *
 * @TableName thread_pool_adjust_log
 */
@TableName(value = "thread_pool_adjust_log")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ThreadPoolAdjustLog implements Serializable {
    /**
     * 唯一标识符，主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /** 线程池配置id*/
    private Long threadPoolConfigId;
    /** 调整前配置 */
    private String beforeAdjustConfig;
    /** 调整后配置 */
    private String adjustConfig;
    /** 状态 0-进行 1-成功 2-失败 */
    private Integer state;
    /**
     * 调整记录的时间
     */
    private Date createTime;
    /** 更新时间 */
    private String updateTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}