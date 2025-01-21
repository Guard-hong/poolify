package cn.poolify.core.manager;

import cn.poolify.core.executor.wrapper.ExecutorWrapper;
import cn.poolify.core.properties.DtpProperties;
import cn.poolify.core.properties.entity.DtpExecutorProps;
import cn.poolify.core.transmitter.ITransmitter;
import cn.poolify.core.transmitter.notifier.NotifyPlatform;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @Author: HCJ
 * @DateTime: 2025/1/21
 * @Description:
 **/
@Slf4j
public final class TransmitterHelper {

    // TODO: spi 加载
    private static final Map<String, ITransmitter> TRANSMITTER_MAP = new ConcurrentHashMap<>();
    // TODO: 配置加载
    private static final List<NotifyPlatform> NOTIFY_PLATFORM_LIST = new ArrayList<>();
    private static final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    private TransmitterHelper() {
    }

    /**
     * TODO: 异步
     * @param executor
     */
    public static void sendAlarmMsg(ExecutorWrapper executor) {
        try {
            lock.readLock().lock();
            NOTIFY_PLATFORM_LIST.forEach(p -> {
                String platform = p.getPlatform();
                Optional.ofNullable(TRANSMITTER_MAP.get(platform))
                        .ifPresent(transmitter -> transmitter.sendAlarmMsg(p, executor));
            });
        } finally {
            lock.readLock().unlock();
        }
    }

    /**
     * TODO: 异步
     * @param newProps
     * @param oldProps
     * @param diffs
     */
    public static void sendNoticeMsg(DtpExecutorProps newProps, DtpExecutorProps oldProps, Set<String> diffs) {
        try {
            lock.readLock().lock();
            NOTIFY_PLATFORM_LIST.forEach(p -> {
                String platform = p.getPlatform();
                Optional.ofNullable(TRANSMITTER_MAP.get(platform))
                        .ifPresent(transmitter -> transmitter.sendNoticeMsg(p, newProps,oldProps,diffs));
            });
        } finally {
            lock.readLock().unlock();
        }
    }

    public static void refresh(DtpProperties dtpProperties) {
        List<NotifyPlatform> newNotifyPlatforms = dtpProperties.getNotifyPlatforms();

        Set<NotifyPlatform> commonElements = new HashSet<>(NOTIFY_PLATFORM_LIST);
        boolean compare = commonElements.retainAll(newNotifyPlatforms);
        // 没有变化
        if (!compare) {
            log.info("notify unchanged");
            return;
        }
        log.info("notify change");
        try {
            lock.writeLock().lock();
            NOTIFY_PLATFORM_LIST.clear();
            NOTIFY_PLATFORM_LIST.addAll(newNotifyPlatforms);
        } finally {
            lock.writeLock().unlock();
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
