package cn.poolify.core.transmitter.assemble;

import cn.poolify.core.executor.wrapper.ExecutorWrapper;
import cn.poolify.core.properties.entity.DtpExecutorProps;

import java.util.Set;

/**
 * @Author: HCJ
 * @DateTime: 2025/1/21
 * @Description:
 **/
public class DefaultAssembler implements IAssembler{
    @Override
    public String assembleAlarm(ExecutorWrapper executor) {
        return null;
    }

    @Override
    public String assembleNotice(DtpExecutorProps newProps, DtpExecutorProps oldProps, Set<String> diffs) {
        return null;
    }
}
