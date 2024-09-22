package cn.poolify.admin.alarm.strategy;

import cn.poolify.admin.config.AlarmProperties;

import javax.annotation.Resource;

/**
 * @Author: HCJ
 * @DateTime: 2024/9/22
 * @Description:
 **/
public abstract class AbstractAlarmStrategy implements IAlarmStrategy{

    @Resource
    protected AlarmProperties alarmProperties;


}
