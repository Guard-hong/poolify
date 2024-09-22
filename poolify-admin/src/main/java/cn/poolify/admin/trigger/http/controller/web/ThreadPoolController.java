package cn.poolify.admin.trigger.http.controller.web;

import cn.poolify.admin.persistent.mapper.ThreadPoolAdjustLogMapper;
import cn.poolify.admin.persistent.mapper.ThreadPoolConfigMapper;
import cn.poolify.admin.persistent.po.ThreadPoolAdjustLog;
import cn.poolify.admin.persistent.po.ThreadPoolConfig;
import cn.poolify.admin.registry.IRegistryReport;
import cn.poolify.admin.trigger.http.api.dto.thread.QueryThreadPoolConfigDTO;
import cn.poolify.admin.trigger.http.api.dto.thread.UpdateThreadPoolConfigDTO;
import cn.poolify.admin.trigger.http.api.vo.thread.RegistryThreadPoolVO;
import cn.poolify.admin.trigger.http.utils.ReturnUtil;
import cn.poolify.common.exception.DynamicThreadPoolException;
import cn.poolify.common.response.HttpResponse;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

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
    private ThreadPoolConfigMapper threadPoolConfigMapper;

    @Resource
    private Map<String, IRegistryReport> registryReportMap;
    @Resource
    private ThreadPoolAdjustLogMapper threadPoolAdjustLogMapper;

    @PostMapping("/update")
    public HttpResponse<Boolean> updateThreadConfig(@RequestBody UpdateThreadPoolConfigDTO updateThreadPoolConfigDTO) {
        ThreadPoolConfig threadPoolConfig = threadPoolConfigMapper.selectById(updateThreadPoolConfigDTO.getId());
        if(!(threadPoolConfig.getCorePoolSize().equals(updateThreadPoolConfigDTO.getCorePoolSize()) &&
            threadPoolConfig.getMaximumPoolSize().equals(updateThreadPoolConfigDTO.getMaximumPoolSize()) &&
            threadPoolConfig.getQueueCapacity().equals(updateThreadPoolConfigDTO.getQueueCapacity()))){
            threadPoolConfigMapper.updateById(ThreadPoolConfig.builder()
                    .id(updateThreadPoolConfigDTO.getId())
                    .corePoolSize(updateThreadPoolConfigDTO.getCorePoolSize())
                    .maximumPoolSize(updateThreadPoolConfigDTO.getMaximumPoolSize())
                    .queueCapacity(updateThreadPoolConfigDTO.getQueueCapacity())
                    .build());
        }

        IRegistryReport registryReport = registryReportMap.get(updateThreadPoolConfigDTO.getRegistryType());
        try {
            registryReport.report(threadPoolConfig.getAddr(), updateThreadPoolConfigDTO.getApplicationName(), updateThreadPoolConfigDTO.getThreadPoolName(),
                    RegistryThreadPoolVO.builder()
                            .corePoolSize(updateThreadPoolConfigDTO.getCorePoolSize())
                            .maximumPoolSize(updateThreadPoolConfigDTO.getMaximumPoolSize())
                            .queueCapacity(updateThreadPoolConfigDTO.getQueueCapacity())
                            .build());
            threadPoolAdjustLogMapper.insert(ThreadPoolAdjustLog.builder()
                    .threadPoolConfigId(threadPoolConfig.getId())
                    .beforeAdjustConfig(JSON.toJSONString(threadPoolConfig))
                    .adjustConfig(JSON.toJSONString(updateThreadPoolConfigDTO))
                    .state(1)
                    .build());
            return ReturnUtil.success(true);
        } catch (DynamicThreadPoolException e) {
            log.error("发布线程池信息: {} 失败", JSON.toJSONString(updateThreadPoolConfigDTO));
            threadPoolAdjustLogMapper.insert(ThreadPoolAdjustLog.builder()
                    .threadPoolConfigId(threadPoolConfig.getId())
                    .beforeAdjustConfig(JSON.toJSONString(threadPoolConfig))
                    .adjustConfig(JSON.toJSONString(updateThreadPoolConfigDTO))
                    .state(2)
                    .build());
            return ReturnUtil.success(false);
        }
    }

    @GetMapping("/list")
    public HttpResponse<Page<ThreadPoolConfig>> queryThreadPoolConfig(QueryThreadPoolConfigDTO queryThreadPoolConfigDTO){
        Page<ThreadPoolConfig> threadPoolConfigPage = threadPoolConfigMapper.selectPage(new Page<>(queryThreadPoolConfigDTO.getCurrent(), queryThreadPoolConfigDTO.getSize()), null);
        return ReturnUtil.success(threadPoolConfigPage);
    }

}
