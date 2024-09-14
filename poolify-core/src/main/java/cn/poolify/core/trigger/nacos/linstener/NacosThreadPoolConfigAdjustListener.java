package cn.poolify.core.trigger.nacos.linstener;

import cn.poolify.common.exception.DynamicThreadPoolException;
import cn.poolify.core.config.properties.NacosRegistryProperties;
import cn.poolify.core.registry.DynamicThreadPoolRegistry;
import cn.poolify.core.registry.model.ThreadPoolParameter;
import cn.poolify.core.trigger.IThreadPoolConfigAdjustListener;
import cn.poolify.core.trigger.nacos.model.ThreadPoolConfigEntity;
import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.annotation.NacosConfigListener;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Author: HCJ
 * @DateTime: 2024/9/14
 * @Description: nacos监听器
 **/
@Slf4j
@AllArgsConstructor
@NoArgsConstructor
public class NacosThreadPoolConfigAdjustListener implements IThreadPoolConfigAdjustListener<String> {

    private NacosRegistryProperties nacosRegistryProperties;


    @Override
    public void onReceived(String newConfig) {
        // 将接收到的 JSON 字符串解析为 ThreadPoolConfigEntity 对象
        ThreadPoolConfigEntity threadPoolConfigEntity = JSON.parseObject(newConfig, ThreadPoolConfigEntity.class);
        try {
            // 修改线程参数
            DynamicThreadPoolRegistry.updateThreadPoolParameter(threadPoolConfigEntity.getThreadPoolName(),
                    ThreadPoolParameter.builder()
                            .corePoolSize(threadPoolConfigEntity.getCorePoolSize())
                            .maximumPoolSize(threadPoolConfigEntity.getMaximumPoolSize())
                            .queueCapacity(threadPoolConfigEntity.getQueueCapacity())
                            .build());

            // todo 通过feign修改数据库
        }catch (DynamicThreadPoolException e){
            log.error("线程池: ${} 修改失败",threadPoolConfigEntity.getThreadPoolName());
        }catch (Exception e){

        }

    }

    @PostConstruct
    @Override
    public void registryListener() {
        try {
            ConfigService configService = NacosFactory.createConfigService(nacosRegistryProperties.getHost()+":"+nacosRegistryProperties.getPort());
            // 获取 dataId 和 groupId
            String dataId = nacosRegistryProperties.getDataId();
            String groupId = nacosRegistryProperties.getGroupId();

            // 注册 Nacos 配置监听器
            configService.addListener(dataId, groupId, new Listener() {
                @Override
                public Executor getExecutor() {
                    return null;
                }

                @Override
                public void receiveConfigInfo(String configInfo) {
                    onReceived(configInfo);
                }

            });
        } catch (NacosException e) {
            throw new RuntimeException(e);
        }
    }

}
