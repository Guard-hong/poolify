package cn.poolify.core.aware;

import cn.poolify.core.aware.impl.TaskTimeoutAware;
import cn.poolify.core.executor.wrapper.ExecutorWrapper;

/**
 * @Author: HCJ
 * @DateTime: 2024/12/22
 * @Description:
 **/
public class AwareManager {

    // TODO: 如果有多个 ExecutorAware，使用List配合order进行排序执行
    private static final ExecutorAware EXECUTOR_AWARE = new TaskTimeoutAware();

    public static void executor(ExecutorWrapper executor, Runnable r) {
        EXECUTOR_AWARE.execute(executor, r);
    }

    public static Runnable beforeExecutor(ExecutorWrapper executor, Runnable r, Thread t) {
        return EXECUTOR_AWARE.beforeExecuteWrap(executor, r, t);
    }

    public static Runnable afterExecutor(ExecutorWrapper executor, Runnable r) {
        return EXECUTOR_AWARE.afterExecutor(executor, r);
    }

}
