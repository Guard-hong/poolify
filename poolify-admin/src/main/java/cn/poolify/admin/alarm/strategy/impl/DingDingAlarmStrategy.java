package cn.poolify.admin.alarm.strategy.impl;

import cn.poolify.admin.alarm.strategy.AbstractAlarmStrategy;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiRobotSendRequest;
import com.dingtalk.api.response.OapiRobotSendResponse;
import com.taobao.api.ApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @Author: HCJ
 * @DateTime: 2024/9/22
 * @Description:
 **/
@Slf4j
@Component("dingding")
public class DingDingAlarmStrategy extends AbstractAlarmStrategy {



    @Override
    public String mark() {
        return "dingding";
    }

    @Override
    public void send(String alarmMessage) throws ApiException {
        String token = alarmProperties.getToken();

        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/robot/send");
        OapiRobotSendRequest req = new OapiRobotSendRequest();

        //定义文本内容
        OapiRobotSendRequest.Text text = new OapiRobotSendRequest.Text();
        text.setContent(alarmMessage);

        //定义 @ 对象
        OapiRobotSendRequest.At at = new OapiRobotSendRequest.At();
        at.setIsAtAll(true);

        //设置消息类型
        req.setMsgtype("text");
        req.setText(text);
        req.setAt(at);
        OapiRobotSendResponse rsp = client.execute(req, token);

        if (rsp.isSuccess()) {
            return;
        }

        throw new ApiException(rsp.getErrcode().toString(), rsp.getErrmsg());
    }
}
