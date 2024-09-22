package cn.poolify.admin.alarm;

import cn.poolify.admin.alarm.model.AlarmMessage;

/**
 * @Author: HCJ
 * @DateTime: 2024/9/22
 * @Description:
 **/
public interface IAlarmService {

    void sendIfThreadPoolHasDanger(AlarmMessage message);

}
