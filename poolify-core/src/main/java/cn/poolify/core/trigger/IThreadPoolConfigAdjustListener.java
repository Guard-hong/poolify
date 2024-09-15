package cn.poolify.core.trigger;

/**
 * @Author: HCJ
 * @DateTime: 2024/9/14
 * @Description: 监听线程变化接口
 **/
public interface IThreadPoolConfigAdjustListener<T> {

     /**
      *
      * @param threadName 修改线程池名字
      * @param newConfig 修改参数
      */
     void onReceived(String threadName,T newConfig);

     void registryListener();
}
