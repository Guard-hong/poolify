package cn.poolify.core.transmitter;

import cn.poolify.core.executor.wrapper.ExecutorWrapper;
import cn.poolify.core.properties.entity.DtpExecutorProps;
import cn.poolify.core.transmitter.assemble.DefaultAssembler;
import cn.poolify.core.transmitter.assemble.IAssembler;
import cn.poolify.core.transmitter.notifier.INotifier;
import cn.poolify.core.transmitter.notifier.NotifyPlatform;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.Set;

/**
 * @Author: HCJ
 * @DateTime: 2025/1/14
 * @Description:
 **/
@Slf4j
public abstract class AbstractTransmitter implements ITransmitter {

    private final INotifier notifier;

    private static final IAssembler DEFAULT_ASSEMBLE = new DefaultAssembler();

    @Getter
    @Setter
    private IAssembler assembler = DEFAULT_ASSEMBLE;

    protected AbstractTransmitter(INotifier notifier) {
        this.notifier = notifier;
    }

    protected AbstractTransmitter(INotifier notifier, IAssembler assembler) {
        this.notifier = notifier;
        this.assembler = assembler;
    }

    @Override
    public void sendNoticeMsg(NotifyPlatform platform, DtpExecutorProps newProps, DtpExecutorProps oldProps, Set<String> diffs) {
        String msg = assembler.assembleNotice(newProps,oldProps,diffs);
        notifier.send(platform, msg);
    }

    @Override
    public void sendAlarmMsg(NotifyPlatform platform, ExecutorWrapper executor) {
        String msg = assembler.assembleAlarm(executor);
        notifier.send(platform, msg);
    }

}