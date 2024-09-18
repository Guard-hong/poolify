package cn.poolify.admin.trigger.http.controller;

import cn.poolify.admin.persistent.mapper.ThreadPoolConfigMapper;
import cn.poolify.admin.persistent.mapper.ThreadPoolRuntimeStatusMapper;
import cn.poolify.admin.persistent.po.ThreadPoolConfig;
import cn.poolify.admin.persistent.po.ThreadPoolRuntimeStatus;
import cn.poolify.admin.registry.IRegistryReport;
import cn.poolify.admin.trigger.http.controller.dto.CollectionThreadPoolConfigDTO;
import cn.poolify.admin.trigger.http.controller.dto.UpdateThreadPoolConfigDTO;
import cn.poolify.admin.trigger.http.controller.vo.RegistryThreadPoolVO;
import cn.poolify.common.exception.DynamicThreadPoolException;
import cn.poolify.common.response.HttpResponse;
import cn.poolify.admin.trigger.http.controller.utils.ReturnUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.exception.NacosException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @Author: HCJ
 * @DateTime: 2024/9/15
 * @Description:
 **/
@Slf4j
@RestController
@RequestMapping("/dynamic_thread")
@CrossOrigin(allowCredentials = "true", originPatterns = {"http://localhost*", "http://127.0.0.1*"})
public class ThreadPoolController {

    @Resource
    private ThreadPoolRuntimeStatusMapper threadPoolRuntimeStatusMapper;
    @Resource
    private ThreadPoolConfigMapper threadPoolConfigMapper;
    @Resource
    private Map<String, IRegistryReport> registryReportMap;

    /**
     * 线程池实时数据采集
     *
     * @param collectionThreadPoolConfigDTO
     * @return
     */
    @PostMapping("/collection")
    public HttpResponse<Boolean> collectionThreadPoolConfig(@RequestBody CollectionThreadPoolConfigDTO collectionThreadPoolConfigDTO) {
        threadPoolRuntimeStatusMapper.save(ThreadPoolRuntimeStatus.builder()
                .applicationName(collectionThreadPoolConfigDTO.getApplicationName())
                .activeThreadCount(collectionThreadPoolConfigDTO.getActiveThreadCount())
                .threadPoolName(collectionThreadPoolConfigDTO.getThreadPoolName())
                .activeThreadCount(collectionThreadPoolConfigDTO.getActiveThreadCount())
                .queueSize(collectionThreadPoolConfigDTO.getQueueSize())
                .completedTaskCount(collectionThreadPoolConfigDTO.getCompletedTaskCount())
                .build());
        return ReturnUtil.success(true);
    }

    @PostMapping("/update")
    public HttpResponse<Boolean> updateThreadConfig(@RequestBody UpdateThreadPoolConfigDTO updateThreadPoolConfigDTO) {
        threadPoolConfigMapper.updateById(ThreadPoolConfig.builder()
                .id(updateThreadPoolConfigDTO.getId())
                .registryType(updateThreadPoolConfigDTO.getRegistryType())
                .addr(updateThreadPoolConfigDTO.getAddr())
                .applicationName(updateThreadPoolConfigDTO.getApplicationName())
                .threadPoolName(updateThreadPoolConfigDTO.getThreadPoolName())
                .corePoolSize(updateThreadPoolConfigDTO.getCorePoolSize())
                .maximumPoolSize(updateThreadPoolConfigDTO.getMaximumPoolSize())
                .queueType(updateThreadPoolConfigDTO.getQueueType())
                .queueCapacity(updateThreadPoolConfigDTO.getQueueCapacity())
//                        .keepAliveTime(updateThreadPoolConfigDTO.get)
//                        .keepAliveTimeUnit
//                        .rejectedPolicy
                .build());
        IRegistryReport registryReport = registryReportMap.get(updateThreadPoolConfigDTO.getRegistryType());
        try {
            registryReport.report(updateThreadPoolConfigDTO.getAddr(), updateThreadPoolConfigDTO.getApplicationName(), updateThreadPoolConfigDTO.getThreadPoolName(),
                    RegistryThreadPoolVO.builder()
                            .corePoolSize(updateThreadPoolConfigDTO.getCorePoolSize())
                            .maximumPoolSize(updateThreadPoolConfigDTO.getMaximumPoolSize())
                            .queueType(updateThreadPoolConfigDTO.getQueueType())
                            .queueCapacity(updateThreadPoolConfigDTO.getQueueCapacity())
                            .rejectedPolicy(updateThreadPoolConfigDTO.getRejectedPolicy())
                            .build());
            return ReturnUtil.success(true);
        } catch (DynamicThreadPoolException e) {
            log.error("发布线程池信息: {} 失败", JSON.toJSONString(updateThreadPoolConfigDTO));
            return ReturnUtil.success(false);
        }
    }

    @PostMapping("/registry")
    public HttpResponse<Boolean> registryThreadPool(@RequestBody ThreadPoolConfig threadPoolConfig) {
        threadPoolConfigMapper.insert(threadPoolConfig);
        return ReturnUtil.success(true);
    }

    @GetMapping("/test")
    public HttpResponse<String> test() {
        return ReturnUtil.success("ok");
    }
}
