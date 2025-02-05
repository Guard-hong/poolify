package cn.poolify.core.test.starter.controller;

import cn.poolify.core.enums.AlarmType;
import cn.poolify.core.executor.ExecutorConverter;
import cn.poolify.core.executor.wrapper.ExecutorWrapper;
import cn.poolify.core.manager.TransmitterHelper;
import cn.poolify.core.properties.entity.DtpExecutorProps;
import cn.poolify.core.test.starter.model.ThreadPoolEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Author: HCJ
 * @DateTime: 2024/9/14
 * @Description:
 **/
@RestController
@RequestMapping(value = "/test")
@CrossOrigin(allowCredentials = "true", originPatterns = {"http://localhost*", "http://127.0.0.1*"})
public class ThreadPoolTestController {
    @Resource
    private Map<String, ThreadPoolExecutor> threadPoolExecutorMap;

    @Resource
    private ExecutorWrapper dtp1;
//    @Resource
//    private ThreadPoolExecutor dtp3;
    @GetMapping("/getThreadPoolList")
    public List<ThreadPoolEntity> ThreadPoolList(){
        ArrayList<ThreadPoolEntity> threadPoolEntities = new ArrayList<>();
        threadPoolExecutorMap.forEach((key,val)->{
            threadPoolEntities.add(ThreadPoolEntity.builder()
                            .threadthreadPoolName(key)
                            .corePoolSize(val.getCorePoolSize())
                            .maximumPoolSize(val.getMaximumPoolSize())
                    .build());
        });
        return threadPoolEntities;
    }

    @GetMapping
    public String test(){
        return "test";
    }

    @GetMapping("dtp1")
    public String getDtp1(){
        DtpExecutorProps dtpExecutorProps = ExecutorConverter.toDtpExecutorProps((ExecutorWrapper) dtp1);
        return "dtp1";
    }
    @GetMapping("sendMsg")
    public String getDtp3(){
        TransmitterHelper.sendAlarmMsg(dtp1, AlarmType.QUEUE_TIMEOUT);
        return "dtp3";
    }
}
