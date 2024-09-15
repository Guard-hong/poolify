package cn.poolify.admin.persistent.mapper;

import cn.poolify.admin.persistent.po.ThreadPoolConfig;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author 洪
* @description 针对表【thread_pool_config(存储线程池的配置信息表)】的数据库操作Mapper
* @createDate 2024-09-13 00:18:38
* @Entity cn.poolify.persistent.po.ThreadPoolConfig
*/
public interface ThreadPoolConfigMapper extends BaseMapper<ThreadPoolConfig> {

    void updateThreadPoolConfig(ThreadPoolConfig build);
}




