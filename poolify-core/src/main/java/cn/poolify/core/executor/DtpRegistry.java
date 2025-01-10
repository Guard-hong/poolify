package cn.poolify.core.executor;

import cn.poolify.core.executor.wrapper.ExecutorWrapper;
import cn.poolify.core.properties.DtpProperties;
import cn.poolify.core.properties.entity.DtpExecutorProps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: HCJ
 * @DateTime: 2024/9/14
 * @Description: 动态线程池注册和管理
 **/
@Slf4j
public class DtpRegistry {

    private static final Map<String, ExecutorWrapper> DYNAMIC_EXECUTORS = new ConcurrentHashMap<>();

    public static void register(String poolName,ExecutorWrapper executorWrapper){
        DYNAMIC_EXECUTORS.putIfAbsent(poolName,executorWrapper);
    }

    public static void refresh(DtpProperties dtpProperties){
        dtpProperties.getExecutors().forEach(DtpRegistry::refresh);
    }

    private static void refresh(DtpExecutorProps props) {
        if (Objects.isNull(props) || StringUtils.isBlank(props.getThreadPoolName())) {
            log.warn("DynamicTp refresh, thread pool name must not be blank, executorProps: {}", props);
            return;
        }
        ExecutorWrapper executorWrapper = DYNAMIC_EXECUTORS.get(props.getThreadPoolName());
        if (Objects.nonNull(executorWrapper)) {
            refresh(executorWrapper, props);
            return;
        }
        log.warn("DynamicTp refresh, cannot find specified executor, name: {}.", props.getThreadPoolName());
    }


    /**
     *
     * @param executorWrapper
     * @param props
     */
    private static void refresh(ExecutorWrapper executorWrapper, DtpExecutorProps props) {
        if (props.coreParamIsInValid()) {
            log.error("DynamicTp refresh, invalid parameters exist, properties: {}", props);
            return;
        }
        DtpExecutorProps oldProps = ExecutorConverter.toDtpExecutorProps(executorWrapper);
        doRefresh(executorWrapper, props);
        DtpExecutorProps newProps = ExecutorConverter.toDtpExecutorProps(executorWrapper);
        if (oldProps.equals(newProps)){
            log.debug("DynamicTp refresh, main properties of [{}] have not changed.",
                    executorWrapper.getThreadPoolName());
            return;
        }
        // TODO: 更新差异日志
    }

    private static void doRefresh(ExecutorWrapper executor, DtpExecutorProps props) {
        if(!Objects.equals(executor.getRunTimeout(),props.getRunTimeout())){
            executor.setRunTimeout(props.getRunTimeout());
        }
        if(!Objects.equals(executor.getQueueTimeout(),props.getQueueTimeout())){
            executor.setQueueTimeout(props.getQueueTimeout());
        }
        doRefreshPoolSize(executor,props);
        if (!Objects.equals(executor.getKeepAliveTime(props.getUnit()), props.getKeepAliveTime())) {
            executor.setKeepAliveTime(props.getKeepAliveTime(), props.getUnit());
        }
    }

    private static void doRefreshPoolSize(ExecutorWrapper executor, DtpExecutorProps props) {
        if (props.getMaximumPoolSize() < executor.getMaximumPoolSize()) {
            if (!Objects.equals(executor.getCorePoolSize(), props.getCorePoolSize())) {
                executor.setCorePoolSize(props.getCorePoolSize());
            }
            if (!Objects.equals(executor.getMaximumPoolSize(), props.getMaximumPoolSize())) {
                executor.setMaximumPoolSize(props.getMaximumPoolSize());
            }
            return;
        }
        if (!Objects.equals(executor.getMaximumPoolSize(), props.getMaximumPoolSize())) {
            executor.setMaximumPoolSize(props.getMaximumPoolSize());
        }
        if (!Objects.equals(executor.getCorePoolSize(), props.getCorePoolSize())) {
            executor.setCorePoolSize(props.getCorePoolSize());
        }
    }

}
