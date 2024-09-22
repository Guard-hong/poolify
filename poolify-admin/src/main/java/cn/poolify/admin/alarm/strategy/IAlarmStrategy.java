package cn.poolify.admin.alarm.strategy;

import com.taobao.api.ApiException;

/**
 * @Author: HCJ
 * @DateTime: 2024/9/22
 * @Description:
 **/
public interface IAlarmStrategy {

    String mark();

    void send(String alarmMessage) throws ApiException;


}
