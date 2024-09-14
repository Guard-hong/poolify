package cn.poolify.admin.persistent.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import cn.poolify.admin.persistent.po.ThreadPoolAdjustLog;

/**
* @author 洪
* @description 针对表【thread_pool_adjust_log(记录每次对线程池进行的动态调整操作，包括线程池大小的调整及原因)】的数据库操作Mapper
* @createDate 2024-09-13 00:18:39
* @Entity cn.poolify.persistent.po.ThreadPoolAdjustLog
*/
public interface ThreadPoolAdjustLogMapper extends BaseMapper<ThreadPoolAdjustLog> {

}




