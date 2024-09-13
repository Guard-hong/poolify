package cn.poolify.test.starter.service;

import cn.poolify.test.starter.model.ThreadPoolValObj;
import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.api.config.annotation.NacosConfigListener;
import org.springframework.stereotype.Component;

/**
 * @Author: HCJ
 * @DateTime: 2024/9/13
 * @Description:
 **/
@Component
public class ThreadPoolConfigUpdater {

    @NacosConfigListener(dataId = "dynamic-threadpool-config-tset", groupId = "DEFAULT_GROUP")
    public void onReceived(String newConfig) {
        // 将接收到的 JSON 字符串解析为 ThreadPoolValObj 对象
        ThreadPoolValObj threadPoolValObj = JSON.parseObject(newConfig, ThreadPoolValObj.class);

        // 打印接收到的配置内容
        System.out.println("Received new ThreadPool configuration: " + threadPoolValObj);

        // 你可以在这里根据新配置动态调整线程池参数
        System.out.println("ThreadPoolValObj configuration updated from Nacos.");
    }
}
