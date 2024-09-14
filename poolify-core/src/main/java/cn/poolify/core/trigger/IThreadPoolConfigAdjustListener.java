package cn.poolify.core.trigger;

/**
 * @Author: HCJ
 * @DateTime: 2024/9/14
 * @Description: 监听线程变化接口
 **/
public interface IThreadPoolConfigAdjustListener<T> {

     void onReceived(T newConfig);

     void registryListener();
}
