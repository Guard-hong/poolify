package cn.poolify.common.exception;

import lombok.Getter;

/**
 * @Author: HCJ
 * @DateTime: 2024/9/14
 * @Description:
 **/

@Getter
public enum ErrorCode {
    THREAD_POOL_NOT_EXIST("error_001","线程池不存在"),
    REPORT_THREAD_POOL_CONFIG_FAIL("error_002","发布线程池配置失败")
    ;

    /**
     * 状态码
     */
    private final String code;

    /**
     * 信息
     */
    private final String message;

    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
