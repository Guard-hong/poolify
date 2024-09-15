package cn.poolify.admin.trigger.http.controller;

import cn.poolify.admin.persistent.mapper.ThreadPoolConfigMapper;
import cn.poolify.admin.persistent.mapper.ThreadPoolRuntimeStatusMapper;
import cn.poolify.admin.persistent.po.ThreadPoolConfig;
import cn.poolify.admin.persistent.po.ThreadPoolRuntimeStatus;
import cn.poolify.admin.trigger.http.controller.dto.CollectionThreadPoolConfigDTO;
import cn.poolify.admin.trigger.http.controller.dto.UpdateThreadPoolConfigDTO;
import cn.poolify.common.response.HttpResponse;
import cn.poolify.admin.trigger.http.controller.utils.ReturnUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author: HCJ
 * @DateTime: 2024/9/15
 * @Description:
 **/
@RestController
@RequestMapping("/dynamic_thread")
@CrossOrigin(allowCredentials = "true", originPatterns = {"http://localhost*", "http://127.0.0.1*"})
public class ThreadPoolController {

    @Resource
    private ThreadPoolRuntimeStatusMapper threadPoolRuntimeStatusMapper;
    @Resource
    private ThreadPoolConfigMapper threadPoolConfigMapper;

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
        threadPoolConfigMapper.updateThreadPoolConfig(ThreadPoolConfig.builder()
                .applicationName(updateThreadPoolConfigDTO.getApplicationName())
                .threadPoolName(updateThreadPoolConfigDTO.getThreadPoolName())
                .corePoolSize(updateThreadPoolConfigDTO.getCorePoolSize())
                .maximumPoolSize(updateThreadPoolConfigDTO.getMaximumPoolSize())
                .queueType(updateThreadPoolConfigDTO.getQueueType())
                .queueCapacity(updateThreadPoolConfigDTO.getQueueCapacity())
                .rejectedPolicy(updateThreadPoolConfigDTO.getRejectedPolicy())
                .build());
        return ReturnUtil.success(true);
    }

    @GetMapping("/test")
    public HttpResponse<String> test(){
        return ReturnUtil.success("ok");
    }
}
