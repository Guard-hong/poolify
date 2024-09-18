package cn.poolify.core.feign;

import cn.poolify.common.response.HttpResponse;
import cn.poolify.core.registry.model.val.CollectionThreadPoolConfigVO;
import cn.poolify.core.registry.model.val.RegistryThreadPoolConfigVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * @Author: HCJ
 * @DateTime: 2024/9/18
 * @Description:
 **/
@AllArgsConstructor
@Slf4j
public class ManagementFeign {

    private WebClient webClient;

    public void collectionThreadPoolData(CollectionThreadPoolConfigVO collectionThreadPoolConfigVO){
        webClient.post()
                .uri("/dynamic_thread/collection")
                .bodyValue(collectionThreadPoolConfigVO)
                .retrieve() // 发起请求并准备处理响应
                .bodyToMono(HttpResponse.class) // 将响应体转换为字符串
                .subscribe(
                        responseBody -> {
                            // 处理成功响应
                            log.info("Response body: {}",responseBody);
                        },
                        error -> {
                            // 处理异常
                            log.error("Request failed: {}",error.getMessage());
                        }
                );
    }

    public void registryThreadPool(RegistryThreadPoolConfigVO registryThreadPoolConfigVO){
        webClient.post()
                .uri("/dynamic_thread/registry")
                .bodyValue(registryThreadPoolConfigVO)
                .retrieve()
                .bodyToMono(HttpResponse.class)
                .subscribe(
                        responseBody -> {
                            // 处理成功响应
                            log.info("Response body: {}",responseBody);
                        },
                        error -> {
                            // 处理异常
                            log.error("Request failed: {}",error.getMessage());
                        }
                );
    }
}
