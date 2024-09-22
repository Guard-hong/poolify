package cn.poolify.admin.persistent.mapper;

import cn.poolify.admin.trigger.http.api.vo.thread.ThreadPoolAdjustLineVO;
import cn.poolify.admin.trigger.http.api.vo.thread.ThreadPoolAdjustPieVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import cn.poolify.admin.persistent.po.ThreadPoolAdjustLog;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
* @author 洪
* @description 针对表【thread_pool_adjust_log(记录每次对线程池进行的动态调整操作，包括线程池大小的调整及原因)】的数据库操作Mapper
* @createDate 2024-09-13 00:18:39
* @Entity cn.poolify.persistent.po.ThreadPoolAdjustLog
*/
public interface ThreadPoolAdjustLogMapper extends BaseMapper<ThreadPoolAdjustLog> {

    List<ThreadPoolAdjustLineVO> selectThreadPoolAdjustLogByLine(@Param("beginDate") Date beginDate, @Param("endDate") Date endDate);

    List<ThreadPoolAdjustPieVO> selectThreadPoolAdjustLogByPie(Date beginDate, Date endDate);
}




