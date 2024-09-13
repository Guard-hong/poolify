package cn.poolify.registry;

/**
 * @Author: HCJ
 * @DateTime: 2024/9/14
 * @Description:
 **/
public interface IRegistry {
    String mark();

    /**
     * 上报全部线程池配置信息
     * @param threadPoolConfigEntityList 线程池列表
     */
    void reportThreadPool();

    /**
     * 上报更新线程池配置参数
     * @param updateThreadPoolConfigDTO 更新线程池配置传输对象
     */
    void reportUpdateThreadPoolConfigParameter(UpdateThreadPoolConfigDTO updateThreadPoolConfigDTO);
}
