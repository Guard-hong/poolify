package cn.poolify.admin.trigger.http.controller.feign;

import cn.poolify.admin.alarm.IAlarmService;
import cn.poolify.admin.alarm.model.AlarmMessage;
import cn.poolify.admin.persistent.mapper.ThreadPoolConfigMapper;
import cn.poolify.admin.persistent.mapper.ThreadPoolRuntimeStatusMapper;
import cn.poolify.admin.persistent.po.ThreadPoolConfig;
import cn.poolify.admin.persistent.po.ThreadPoolRuntimeStatus;
import cn.poolify.admin.trigger.http.api.dto.thread.CollectionThreadPoolConfigDTO;
import cn.poolify.admin.trigger.http.utils.ReturnUtil;
import cn.poolify.common.response.HttpResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author: HCJ
 * @DateTime: 2024/9/15
 * @Description: core上报接口
 **/
@Slf4j
@RestController
@RequestMapping("/inner/dynamic_thread")
@CrossOrigin(allowCredentials = "true", originPatterns = {"http://localhost*", "http://127.0.0.1*"})
public class InnerThreadPoolController {

    @Resource
    private ThreadPoolRuntimeStatusMapper threadPoolRuntimeStatusMapper;
    @Resource
    private ThreadPoolConfigMapper threadPoolConfigMapper;
    @Resource
    private IAlarmService alarmService;

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
                .threadPoolName(collectionThreadPoolConfigDTO.getThreadPoolName())
                .activeThreadCount(collectionThreadPoolConfigDTO.getActiveThreadCount())
                .corePoolSize(collectionThreadPoolConfigDTO.getCorePoolSize())
                .maximumPoolSize(collectionThreadPoolConfigDTO.getMaximumPoolSize())
                .queueSize(collectionThreadPoolConfigDTO.getQueueSize())
                .completedTaskCount(collectionThreadPoolConfigDTO.getCompletedTaskCount())
                .build());
        // todo 警告
        alarmService.sendIfThreadPoolHasDanger(AlarmMessage.builder()
                .messageContent(AlarmMessage.MessageContent.builder()
                        .applicationName(collectionThreadPoolConfigDTO.getApplicationName())
                        .threadPoolName(collectionThreadPoolConfigDTO.getThreadPoolName())
                        .activeThreadCount(collectionThreadPoolConfigDTO.getActiveThreadCount())
                        .corePoolSize(collectionThreadPoolConfigDTO.getCorePoolSize())
                        .maximumPoolSize(collectionThreadPoolConfigDTO.getMaximumPoolSize())
                        .queueSize(collectionThreadPoolConfigDTO.getQueueSize()).build())
                .build());
        return ReturnUtil.success(true);
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
