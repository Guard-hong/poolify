package cn.poolify.core.executor;

import java.util.concurrent.*;

/**
 * @Author: HCJ
 * @DateTime: 2024/12/30
 * @Description:
 **/
public class DtpExecutor extends ExecutorWrapper{
    public DtpExecutor(String name, ThreadPoolExecutor originExecutor) {
        super(name, originExecutor);
    }
    /**
     * 用于配置创建
     * @param corePoolSize
     * @param maximumPoolSize
     * @param keepAliveTime
     * @param unit
     * @param queue
     * @param factory
     * @param rejectedExecutionHandler
     */
    public DtpExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime,
                           TimeUnit unit, BlockingQueue<Runnable> queue, ThreadFactory factory,
                           RejectedExecutionHandler rejectedExecutionHandler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, queue, factory, rejectedExecutionHandler);
    }
}
