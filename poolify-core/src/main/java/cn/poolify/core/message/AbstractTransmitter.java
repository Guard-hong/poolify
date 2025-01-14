package cn.poolify.core.message;

import cn.poolify.core.executor.wrapper.ExecutorWrapper;
import cn.poolify.core.message.assemble.AlarmAssembler;
import cn.poolify.core.message.assemble.IAssembler;
import cn.poolify.core.message.assemble.NoticeAssembler;
import cn.poolify.core.message.notifier.INotifier;
import cn.poolify.core.message.notifier.NotifyPlatform;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author: HCJ
 * @DateTime: 2025/1/14
 * @Description:
 **/
@Slf4j
public abstract class AbstractTransmitter implements ITransmitter {

    private final INotifier notifier;

    private static final IAssembler DEFAULT_ALARM_ASSEMBLER = new AlarmAssembler();
    private static final IAssembler DEFAULT_NOTICE_ASSEMBLER = new NoticeAssembler();

    protected AbstractTransmitter(INotifier notifier) {
        this.notifier = notifier;
    }

    @Override
    public void sendNoticeMsg(NotifyPlatform platform, ExecutorWrapper executor) {
        sendNoticeMsg(platform, executor, DEFAULT_NOTICE_ASSEMBLER);
    }

    @Override
    public void sendNoticeMsg(NotifyPlatform platform, ExecutorWrapper executor, IAssembler assembler) {
        doSendMsg(platform, executor, assembler);
    }

    @Override
    public void sendAlarmMsg(NotifyPlatform platform, ExecutorWrapper executor) {
        sendAlarmMsg(platform, executor, DEFAULT_ALARM_ASSEMBLER);
    }

    @Override
    public void sendAlarmMsg(NotifyPlatform platform, ExecutorWrapper executor, IAssembler assembler) {
        doSendMsg(platform, executor, assembler);
    }

    private void doSendMsg(NotifyPlatform platform, ExecutorWrapper executor, IAssembler assembler) {
        String msg = assembler.assemble(executor);
        notifier.send(platform, msg);
    }
}