package cn.poolify.core.wrapper;

import cn.poolify.core.monitor.ExecutorMonitor;
import lombok.Data;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Author: HCJ
 * @DateTime: 2024/12/21
 * @Description: 对象线程池进行增强 -- 添加监控
 **/
@Data
public class ExecutorWrapper {

    private ThreadPoolExecutor executor;
    private ExecutorMonitor executorMonitor;

    public ExecutorWrapper(ThreadPoolExecutor executor, long queueTimeout, long runTimeout) {
        this.executor = executor;
        this.executorMonitor = new ExecutorMonitor(this, queueTimeout, runTimeout);
    }
    public ExecutorWrapper(ThreadPoolExecutor executor) {
        this.executor = executor;
        this.executorMonitor = new ExecutorMonitor(this);
    }
}
