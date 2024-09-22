package cn.poolify.admin.trigger.http.api.vo.thread;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Author: HCJ
 * @DateTime: 2024/9/19
 * @Description:
 **/
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ThreadPoolAdjustLineVO {

    private Date date;

    private Integer count;
}
