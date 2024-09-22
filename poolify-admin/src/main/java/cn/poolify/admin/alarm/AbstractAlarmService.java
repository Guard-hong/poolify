package cn.poolify.admin.alarm;

import cn.poolify.admin.alarm.model.AlarmMessage;

/**
 * @Author: HCJ
 * @DateTime: 2024/9/22
 * @Description:
 **/
public abstract class AbstractAlarmService implements IAlarmService{

    public boolean isSendIfThreadPoolHasDanger(AlarmMessage alarmMessage){
        AlarmMessage.MessageContent messageContent = alarmMessage.getMessageContent();
        return messageContent.getActiveThreadCount() >= messageContent.getMaximumPoolSize() * 0.8;
    }
}
