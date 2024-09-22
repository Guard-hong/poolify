package cn.poolify.admin.trigger.http.controller.web;

import cn.poolify.admin.alarm.strategy.IAlarmStrategy;
import cn.poolify.admin.alarm.model.AlarmMessage;
import com.taobao.api.ApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author: HCJ
 * @DateTime: 2024/9/22
 * @Description:
 **/
@Slf4j
@RestController
@RequestMapping("/alarm")
@CrossOrigin(allowCredentials = "true", originPatterns = {"http://localhost*", "http://127.0.0.1*"})
public class AlarmMessageTestController {

    @Resource
    private IAlarmStrategy alarmStrategy;

    @GetMapping("/test")
    public void sendIfThreadPoolHasDanger() throws ApiException {
        alarmStrategy.send(AlarmMessage.buildMessageContent(AlarmMessage.builder()
                .messageContent(AlarmMessage.MessageContent.builder()
                        .applicationName("poolify-test")
                        .threadPoolName("threadPoolExecutor01")
                        .activeThreadCount(11)
                        .corePoolSize(10)
                        .maximumPoolSize(15)
                        .queueSize(2).build())
                .build()));
    }
}
