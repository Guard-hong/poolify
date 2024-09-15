package cn.poolify.admin.trigger.http.controller.utils;

import cn.poolify.common.response.HttpErrorCode;
import cn.poolify.common.response.HttpResponse;

/**
 * @Author: HCJ
 * @DateTime: 2024/9/15
 * @Description:
 **/
public class ReturnUtil {

    public static <T> HttpResponse<T> success(T data){
        return new HttpResponse<>(data, HttpErrorCode.SUCCESS);
    }
}
