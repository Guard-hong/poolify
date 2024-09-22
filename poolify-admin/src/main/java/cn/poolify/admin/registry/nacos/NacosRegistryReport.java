package cn.poolify.admin.registry.nacos;

import cn.poolify.admin.registry.IRegistryReport;
import cn.poolify.admin.trigger.http.api.vo.thread.RegistryThreadPoolVO;
import cn.poolify.common.exception.DynamicThreadPoolException;
import cn.poolify.common.exception.ErrorCode;
import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.exception.NacosException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @Author: HCJ
 * @DateTime: 2024/9/18
 * @Description:
 **/
@Component("nacos")
@Slf4j
public class NacosRegistryReport implements IRegistryReport {
    @Override
    public void report(String addr,String applicationName,String threadPoolName,RegistryThreadPoolVO registryThreadPoolVO) {
        String reportJSON = JSON.toJSONString(registryThreadPoolVO);
        try {
            ConfigService configService = NacosFactory.createConfigService(addr);
            configService.publishConfig(threadPoolName, applicationName,
                    reportJSON);
        } catch (NacosException e) {
            log.error("发布线程池信息: {} 失败",reportJSON);
            throw new DynamicThreadPoolException(ErrorCode.REPORT_THREAD_POOL_CONFIG_FAIL);
        }
    }
}
