package cn.poolify.admin.registry;

import cn.poolify.admin.trigger.http.controller.vo.RegistryThreadPoolVO;
import cn.poolify.common.exception.DynamicThreadPoolException;
import com.alibaba.fastjson.JSON;

/**
 * @Author: HCJ
 * @DateTime: 2024/9/18
 * @Description:
 **/
public interface IRegistryReport {


    void report(String addr,String applicationName,String threadPoolName,RegistryThreadPoolVO registryThreadPoolVO) throws DynamicThreadPoolException;
}
