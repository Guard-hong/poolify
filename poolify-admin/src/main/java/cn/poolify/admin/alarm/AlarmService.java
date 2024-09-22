package cn.poolify.admin.alarm;

import cn.poolify.admin.alarm.model.AlarmMessage;
import cn.poolify.admin.alarm.strategy.IAlarmStrategy;
import cn.poolify.admin.config.AlarmProperties;
import com.taobao.api.ApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Author: HCJ
 * @DateTime: 2024/9/22
 * @Description:
 **/
@Slf4j
@Service
public class AlarmService extends AbstractAlarmService {

    @Resource
    public Map<String,IAlarmStrategy> alarmStrategyMap;

    @Resource
    public AlarmProperties alarmProperties;

    @Override
    @Async
    public void sendIfThreadPoolHasDanger(AlarmMessage message) {
        if(!isSendIfThreadPoolHasDanger(message)){
            return ;
        }
        // 构造消息
        String messageContent = AlarmMessage.buildMessageContent(message);
        // 得到指定的发送方式
        List<String> usePlatform = alarmProperties.getUsePlatform();
        usePlatform.forEach(key->{
            IAlarmStrategy alarmStrategy = alarmStrategyMap.get(key);
            try {
                alarmStrategy.send(messageContent);
            } catch (ApiException e) {
                throw new RuntimeException(e);
            }
        });
    }


}
