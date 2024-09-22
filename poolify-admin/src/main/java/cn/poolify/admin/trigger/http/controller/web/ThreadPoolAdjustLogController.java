package cn.poolify.admin.trigger.http.controller.web;

import cn.poolify.admin.persistent.mapper.ThreadPoolAdjustLogMapper;
import cn.poolify.admin.trigger.http.api.dto.thread.ThreadPoolAdjustLogDTO;
import cn.poolify.admin.trigger.http.utils.ReturnUtil;
import cn.poolify.admin.trigger.http.api.vo.thread.ThreadPoolAdjustLineVO;
import cn.poolify.admin.trigger.http.api.vo.thread.ThreadPoolAdjustPieVO;
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
@RequestMapping("/dynamic_thread_log")
@CrossOrigin(allowCredentials = "true", originPatterns = {"http://localhost*", "http://127.0.0.1*"})
public class ThreadPoolAdjustLogController {
    @Resource
    private ThreadPoolAdjustLogMapper threadPoolAdjustLogMapper;
    @GetMapping("/adjust_log_line")
    public HttpResponse<List<ThreadPoolAdjustLineVO>> adjustLogLine(ThreadPoolAdjustLogDTO threadPoolAdjustLogDTO){
        List<ThreadPoolAdjustLineVO> threadPoolAdjustLineVOS =
                threadPoolAdjustLogMapper.selectThreadPoolAdjustLogByLine(threadPoolAdjustLogDTO.getBeginDate(), threadPoolAdjustLogDTO.getEndDate());
        return ReturnUtil.success(threadPoolAdjustLineVOS);
    }

    @GetMapping("/adjust_log_pie")
    public HttpResponse<List<ThreadPoolAdjustPieVO>> adjustLogPie(ThreadPoolAdjustLogDTO threadPoolAdjustLogDTO){
        List<ThreadPoolAdjustPieVO> threadPoolAdjustLineVOS =
                threadPoolAdjustLogMapper.selectThreadPoolAdjustLogByPie(threadPoolAdjustLogDTO.getBeginDate(), threadPoolAdjustLogDTO.getEndDate());
        return ReturnUtil.success(threadPoolAdjustLineVOS);
    }
}
