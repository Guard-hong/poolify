package cn.poolify.admin.trigger.http.api.vo.thread;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


/**
 * @Author: HCJ
 * @DateTime: 2024/9/19
 * @Description:
 **/
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ThreadPoolAdjustPieVO {

    private Integer state;

    private Integer count;
}
