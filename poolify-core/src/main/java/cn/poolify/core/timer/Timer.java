package cn.poolify.core.timer;

import java.util.concurrent.TimeUnit;

/**
 * @Author: HCJ
 * @DateTime: 2024/12/17
 * @Description: 计时器
 **/
public interface Timer {
    // 创建一个定时器

    /**
     *
     * @param timeTask
     * @param delay
     * @param unit
     * @return
     */
    Timeout createTimeout(TimeTask timeTask, long delay, TimeUnit unit);
}
