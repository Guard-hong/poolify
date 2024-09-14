package cn.poolify.common.exception;

import lombok.Data;

/**
 * @Author: HCJ
 * @DateTime: 2024/9/14
 * @Description:
 **/
@Data
public class DynamicThreadPoolException extends RuntimeException{
        /**
         * 错误码
         */
        private final String code;

        private final String message;

        public DynamicThreadPoolException(String code, String message) {
            this.code = code;
            this.message = message;
        }

        public DynamicThreadPoolException(ErrorCode errorCode) {
            this.code = errorCode.getCode();
            this.message = errorCode.getMessage();
        }
}
