package cn.poolify.admin.trigger.http.controller.web;

import cn.poolify.admin.persistent.mapper.ThreadPoolRuntimeStatusMapper;
import cn.poolify.admin.persistent.po.ThreadPoolRuntimeStatus;
import cn.poolify.admin.trigger.http.api.dto.thread.ThreadPoolRuntimeStatusLineDTO;
import cn.poolify.admin.trigger.http.api.vo.thread.ThreadPoolRuntimeStatusLineVO;
import cn.poolify.admin.trigger.http.utils.ReturnUtil;
import cn.poolify.common.response.HttpResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: HCJ
 * @DateTime: 2024/9/21
 * @Description:
 **/

@Slf4j
@RestController
@RequestMapping("/dynamic_thread_runtime_status")
@CrossOrigin(allowCredentials = "true", originPatterns = {"http://localhost*", "http://127.0.0.1*"})
public class ThreadPoolRuntimeStatusController {

    @Resource
    private ThreadPoolRuntimeStatusMapper threadPoolRuntimeStatusMapper;


    @GetMapping("/line")
    public HttpResponse<List<ThreadPoolRuntimeStatusLineVO>> getLineChart(ThreadPoolRuntimeStatusLineDTO threadPoolRuntimeStatusLineDTO){
        List<ThreadPoolRuntimeStatusLineVO> threadPoolRuntimeStatusLineVOList=threadPoolRuntimeStatusMapper.getLineChart(ThreadPoolRuntimeStatus.builder()
                        .applicationName(threadPoolRuntimeStatusLineDTO.getApplicationName())
                        .threadPoolName(threadPoolRuntimeStatusLineDTO.getThreadPoolName())
                .build());
        return ReturnUtil.success(threadPoolRuntimeStatusLineVOList);
    }



}
