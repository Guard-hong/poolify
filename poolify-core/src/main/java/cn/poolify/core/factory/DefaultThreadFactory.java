package cn.poolify.core.factory;

import java.util.concurrent.ThreadFactory;

/**
 * @Author: HCJ
 * @DateTime: 2024/12/24
 * @Description:
 **/
public class DefaultThreadFactory implements ThreadFactory {
    @Override
    public Thread newThread(Runnable r) {
        return new Thread(r);
    }
}
