package cn.poolify.core.trigger.listener.nacos;

import cn.poolify.common.exception.DynamicThreadPoolException;
import cn.poolify.core.config.properties.NacosRegistryProperties;
import cn.poolify.core.registry.DynamicThreadPoolRegistry;
import cn.poolify.core.registry.model.entity.RegistryThreadPool;
import cn.poolify.core.trigger.IThreadPoolConfigAdjustListener;
import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.PostConstruct;
import java.util.concurrent.Executor;

/**
 * @Author: HCJ
 * @DateTime: 2024/9/14
 * @Description: nacos监听器
 **/
@Slf4j
@AllArgsConstructor
@NoArgsConstructor
public class NacosThreadPoolConfigAdjustListener implements IThreadPoolConfigAdjustListener {

    private NacosRegistryProperties nacosRegistryProperties;
    private ConfigService configService;
    private DynamicThreadPoolRegistry dynamicThreadPoolRegistry;

    @Override
    public void onReceived(String threadPoolName, RegistryThreadPool registryThreadPool) {
        try {
            // 修改线程参数
            dynamicThreadPoolRegistry.updateThreadPoolParameter(threadPoolName, registryThreadPool);

        } catch (DynamicThreadPoolException e) {
            log.error("线程池: {} 修改失败", threadPoolName);
        } catch (Exception e) {

        }

    }

    @PostConstruct
    @Override
    public void registryListener() {
        // 获取 groupId
        String groupId = nacosRegistryProperties.getGroupId();

        // 注册 Nacos 配置监听器
        dynamicThreadPoolRegistry.getAllThreadPools().forEach((key, val) -> {
            try {
                configService.addListener(key, groupId, new Listener() {
                    @Override
                    public Executor getExecutor() {
                        return null;
                    }

                    @Override
                    public void receiveConfigInfo(String configInfo) {
                        // 将接收到的 JSON 字符串解析为 ThreadPoolConfigEntity 对象
                        RegistryThreadPool registryThreadPool = JSON.parseObject(configInfo, RegistryThreadPool.class);
                        onReceived(key, registryThreadPool);
                    }

                });
            } catch (NacosException e) {
                throw new RuntimeException(e);
            }
        });


    }

}
