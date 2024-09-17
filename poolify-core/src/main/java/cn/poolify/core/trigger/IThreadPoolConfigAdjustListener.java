package cn.poolify.core.trigger;

import cn.poolify.core.registry.model.entity.RegistryThreadPool;

/**
 * @Author: HCJ
 * @DateTime: 2024/9/14
 * @Description: 监听线程池参数变化接口
 **/
public interface IThreadPoolConfigAdjustListener {

     /**
      * 执行修改
      * @param threadName 修改线程池名字
      * @param newConfig 修改参数
      */
     void onReceived(String threadName, RegistryThreadPool newConfig);

     /**
      * 注册监听事件
      */
     void registryListener();
}
