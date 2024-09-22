package cn.poolify.admin.trigger.http.api.vo.auth;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: HCJ
 * @DateTime: 2024/9/19
 * @Description:
 **/
@Data
@AllArgsConstructor
public class CheckAuthVO implements Serializable {
    Boolean isLogin;
}