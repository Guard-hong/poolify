package cn.poolify.common.response;

import lombok.Getter;

/**
 * @Author: HCJ
 * @DateTime: 2024/9/15
 * @Description:
 **/
@Getter
public enum HttpErrorCode {

    SUCCESS(0,"成功"),
    FAIL(4,"失败");
    /**
     * 状态码
     */
    private final int code;

    /**
     * 信息
     */
    private final String message;

    HttpErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
