package cn.poolify.core.message;

import cn.poolify.core.executor.wrapper.ExecutorWrapper;
import cn.poolify.core.message.assemble.AlarmAssembler;
import cn.poolify.core.message.assemble.IAssembler;
import cn.poolify.core.message.assemble.NoticeAssembler;
import cn.poolify.core.message.notifier.INotifier;
import cn.poolify.core.message.notifier.NotifyPlatform;
import lombok.Getter;
import lombok.Setter;
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

    @Getter
    @Setter
    private IAssembler alarmAssembler = DEFAULT_ALARM_ASSEMBLER;
    @Setter
    @Getter
    private IAssembler noticeAssembler = DEFAULT_NOTICE_ASSEMBLER;

    protected AbstractTransmitter(INotifier notifier) {
        this.notifier = notifier;
    }

    protected AbstractTransmitter(INotifier notifier, IAssembler alarmAssembler, IAssembler noticeAssembler) {
        this.notifier = notifier;
        this.alarmAssembler = alarmAssembler;
        this.noticeAssembler = noticeAssembler;
    }

    @Override
    public void sendNoticeMsg(NotifyPlatform platform, ExecutorWrapper executor) {
        doSendMsg(platform, executor, noticeAssembler);
    }

    @Override
    public void sendAlarmMsg(NotifyPlatform platform, ExecutorWrapper executor) {
        doSendMsg(platform, executor, alarmAssembler);
    }

    private void doSendMsg(NotifyPlatform platform, ExecutorWrapper executor, IAssembler assembler) {
        String msg = assembler.assemble(executor);
        notifier.send(platform, msg);
    }
}