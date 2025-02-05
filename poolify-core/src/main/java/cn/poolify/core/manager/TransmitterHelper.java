package cn.poolify.core.manager;

import cn.poolify.core.enums.AlarmType;
import cn.poolify.core.executor.wrapper.ExecutorWrapper;
import cn.poolify.core.properties.DtpProperties;
import cn.poolify.core.properties.entity.DtpExecutorProps;
import cn.poolify.core.transmitter.ITransmitter;
import cn.poolify.core.entity.NotifyPlatform;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @Author: HCJ
 * @DateTime: 2025/1/21
 * @Description:
 * TODO: 指责拆分，增加注册中心
 **/
@Slf4j
public final class TransmitterHelper {

    /**
     * k->发送平台 v->发送实现
     */
    private static final Map<String, ITransmitter> TRANSMITTER_MAP = new ConcurrentHashMap<>();
    private static final List<NotifyPlatform> NOTIFY_PLATFORM_LIST = DtpProperties.getInstance().getNotifyPlatforms();
    private static final ReentrantReadWriteLock LOCK = new ReentrantReadWriteLock();
    // 异步发送消息线程池 TODO:替换成自定义线程池
    private static final ExecutorService EXECUTOR = Executors.newFixedThreadPool(3);

    private TransmitterHelper() {
    }

    static{
        ServiceLoader<ITransmitter> loader  = ServiceLoader.load(ITransmitter.class);
        for (ITransmitter service : loader) {
            TRANSMITTER_MAP.put(service.mark(), service);
        }

    }

    /**
     * @param executor
     */
    public static void sendAlarmMsg(ExecutorWrapper executor, AlarmType type) {
        try {
            LOCK.readLock().lock();
            EXECUTOR.execute(() -> NOTIFY_PLATFORM_LIST.forEach(p -> {
                String platform = p.getPlatform();
                Optional.ofNullable(TRANSMITTER_MAP.get(platform))
                        .ifPresent(transmitter -> transmitter.sendAlarmMsg(p, executor,type));
            }));
        } finally {
            LOCK.readLock().unlock();
        }
    }

    /**
     *
     * @param newProps
     * @param oldProps
     * @param diffs
     */
    public static void sendNoticeMsg(DtpExecutorProps newProps, DtpExecutorProps oldProps, Set<String> diffs) {
        try {
            LOCK.readLock().lock();
            EXECUTOR.execute(() -> NOTIFY_PLATFORM_LIST.forEach(p -> {
                String platform = p.getPlatform();
                Optional.ofNullable(TRANSMITTER_MAP.get(platform))
                        .ifPresent(transmitter -> transmitter.sendNoticeMsg(p, newProps, oldProps, diffs));
            }));
        } finally {
            LOCK.readLock().unlock();
        }
    }

    public static void refresh(DtpProperties dtpProperties) {
        List<NotifyPlatform> newNotifyPlatforms = dtpProperties.getNotifyPlatforms();

        Set<NotifyPlatform> commonElements = new HashSet<>(NOTIFY_PLATFORM_LIST);
        boolean compare = commonElements.retainAll(newNotifyPlatforms);
        // 没有变化
        if (!compare) {
            log.info("notify not change");
            return;
        }
        log.info("notify change");
        try {
            LOCK.writeLock().lock();
            EXECUTOR.execute(()->{
                NOTIFY_PLATFORM_LIST.clear();
                NOTIFY_PLATFORM_LIST.addAll(newNotifyPlatforms);
            });
        } finally {
            LOCK.writeLock().unlock();
        }
    }

    /**
     * TODO: 模拟
     */
    public static void load() {
        ServiceLoader<ITransmitter> load = ServiceLoader.load(ITransmitter.class);
        for (ITransmitter transmitter : load) {
            TRANSMITTER_MAP.put(transmitter.mark(), transmitter);
        }
    }

}
