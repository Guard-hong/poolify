package cn.poolify.core.registry;


import cn.poolify.core.registry.model.entity.ThreadPoolConfigEntity;
import cn.poolify.core.registry.model.entity.RegistryThreadPool;

/**
 * @Author: HCJ
 * @DateTime: 2024/9/14
 * @Description:
 **/
public interface IRegistry<T> {
    String mark();

    /**
     * 上报线程池配置信息
     * @param threadPoolConfigEntity 线程池列表
     */
    void reportThreadPool(ThreadPoolConfigEntity threadPoolConfigEntity) throws Exception;

    /**
     * 上报更新线程池配置参数
     * @param updateThreadPoolConfig 更新线程池配置传输对象
     */
    void adjustThreadPoolConfig(T updateThreadPoolConfig) throws Exception;

    /**
     * 查询配置
     * @param applicationName
     * @param beanName
     * @return
     * @throws Exception
     */
    RegistryThreadPool queryThreadPoolConfig(String applicationName, String beanName) throws Exception;

}
