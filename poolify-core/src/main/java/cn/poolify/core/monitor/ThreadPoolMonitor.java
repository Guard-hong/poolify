package cn.poolify.core.monitor;

import cn.poolify.core.manager.ContextManagerHelper;
import cn.poolify.core.timer.HashedWheelTimer;
import cn.poolify.core.timer.Timeout;
import cn.poolify.core.timer.TimerTask;
import cn.poolify.core.timer.task.QueueTimeoutTimerTask;
import cn.poolify.core.wrapper.ExecutorWrapper;
import lombok.Data;

import java.lang.ref.SoftReference;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.LongAdder;

/**
 * @Author: HCJ
 * @DateTime: 2024/12/21
 * @Description: jk
 **/
@Data
public class ThreadPoolMonitor {

    /**
     * 线程池
     */
    private ExecutorWrapper executorWrapper;

    /**
     * 任务运行超时时间，单位ms
     */
    private long runTimeout = 0;

    /**
     * 任务等待超时时间，单位ms
     */
    private long queueTimeout = 0;

    /**
     * 拒绝任务数
     */
    private final LongAdder rejectCount = new LongAdder();

    /**
     * 运行超时任务数
     */
    private final LongAdder runTimeoutCount = new LongAdder();

    /**
     * 等待超时任务数
     */
    private final LongAdder queueTimeoutCount = new LongAdder();

    /**
     * k->Runnable v->Timer. ps：线程由于异常退出 k=null,v不能正常gc。参考ThreadLocal k-v设计
     */
    private final Map<Runnable, SoftReference<Timeout>> queueTimeoutMap = new ConcurrentHashMap<>();

    public ThreadPoolMonitor(ExecutorWrapper executorWrapper) {
        this.executorWrapper = executorWrapper;
        // TODO: 其他参数补充
    }

    public void startRunTimeoutTask(Thread t,Runnable r){
        // 设置的超时时间不符合
        if(queueTimeout <= 0) return ;
        HashedWheelTimer timer = ContextManagerHelper.geBean(HashedWheelTimer.class);
        TimerTask task = new QueueTimeoutTimerTask(executorWrapper,r);
        queueTimeoutMap.put(r, new SoftReference<>(timer.createTimeout(task,queueTimeout, TimeUnit.MICROSECONDS)));
    }

}