package cn.poolify.admin.persistent.mapper;

import cn.poolify.admin.persistent.po.ThreadPoolRuntimeStatus;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author 洪
* @description 针对表【thread_pool_runtime_status(定期采集并记录线程池的运行时状态，用于监控和分析线程池的表现)】的数据库操作Mapper
* @createDate 2024-09-13 00:18:38
* @Entity cn.poolify.persistent.po.ThreadPoolRuntimeStatus
*/
public interface ThreadPoolRuntimeStatusMapper extends BaseMapper<ThreadPoolRuntimeStatus> {

}




