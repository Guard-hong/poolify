package cn.poolify.common.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: HCJ
 * @DateTime: 2024/9/15
 * @Description:
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HttpResponse<T> {
    private int code;

    private T data;

    private String message;

    public HttpResponse(int code, T data) {
        this(code, data, "");
    }

    public HttpResponse(T data,HttpErrorCode errorCode) {
        this(errorCode.getCode(), data, errorCode.getMessage());
    }
}
