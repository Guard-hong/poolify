package cn.poolify.core.test.starter.controller;

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
}
