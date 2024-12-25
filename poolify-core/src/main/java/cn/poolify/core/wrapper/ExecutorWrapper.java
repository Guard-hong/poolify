package cn.poolify.core.wrapper;

import cn.poolify.core.monitor.ExecutorMonitor;
import cn.poolify.core.executor.ThreadPoolExecutorProxy;
import lombok.Data;

/**
 * @Author: HCJ
 * @DateTime: 2024/12/21
 * @Description: 对象线程池进行增强 -- 添加监控
 **/
@Data
public class ExecutorWrapper {

    private ThreadPoolExecutorProxy executor;
    private ExecutorMonitor executorMonitor;

    public ExecutorWrapper(ThreadPoolExecutorProxy executor) {
        this.executor = executor;
        this.executorMonitor = new ExecutorMonitor(this);
    }
}
