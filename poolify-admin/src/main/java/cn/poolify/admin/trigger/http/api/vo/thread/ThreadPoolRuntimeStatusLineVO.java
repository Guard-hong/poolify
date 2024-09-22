package cn.poolify.admin.trigger.http.api.vo.thread;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Author: HCJ
 * @DateTime: 2024/9/22
 * @Description:
 **/
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ThreadPoolRuntimeStatusLineVO {

    private Date date;

    private Integer activeThreadCount;
}
