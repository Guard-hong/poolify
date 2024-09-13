package cn.poolify.test.starter.controller;

import cn.poolify.test.starter.model.ThreadPoolValObj;
import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.exception.NacosException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author: HCJ
 * @DateTime: 2024/9/14
 * @Description:
 **/
@RestController
public class NacosController {

    @Resource
    private ConfigService configService;


    @PostMapping("/update")
    public String update(@RequestBody ThreadPoolValObj threadPoolValObj) throws NacosException {
        configService.publishConfig("dynamic-threadpool-config-tset","DEFAULT_GROUP", JSON.toJSONString(threadPoolValObj));
        return "ok";
    }
}
