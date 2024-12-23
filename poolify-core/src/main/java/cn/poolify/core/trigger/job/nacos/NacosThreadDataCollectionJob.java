package cn.poolify.core.trigger.job.nacos;

import cn.poolify.core.config.properties.ManagementProperties;
import cn.poolify.core.feign.ManagementFeign;
import cn.poolify.core.registry.DtpRegistry;
import cn.poolify.core.registry.model.val.CollectionThreadPoolConfigVO;
import cn.poolify.core.trigger.IThreadPoolDataCollectionJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;
import java.util.List;


/**
 * @Author: HCJ
 * @DateTime: 2024/9/15
 * @Description:
 **/
@Slf4j
public class NacosThreadDataCollectionJob implements IThreadPoolDataCollectionJob {

    @Resource
    private ManagementProperties managementProperties;
    @Resource
    private DtpRegistry dtpRegistry;
    @Resource
    private ManagementFeign managementFeign;

    @Scheduled(cron = "${poolify.management.collection-cron}")
    @Override
    public void collectionThreadPoolData() {
        List<CollectionThreadPoolConfigVO> allThreadPoolConfig = dtpRegistry.getAllThreadPoolConfig();
        allThreadPoolConfig.forEach(collectionThreadPoolConfigVO ->{
            managementFeign.collectionThreadPoolData(collectionThreadPoolConfigVO);
        });
    }
}
