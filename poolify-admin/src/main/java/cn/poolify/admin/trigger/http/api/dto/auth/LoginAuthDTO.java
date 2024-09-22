package cn.poolify.admin.trigger.http.api.dto.auth;

import lombok.Data;

/**
 * @Author: HCJ
 * @DateTime: 2024/9/19
 * @Description:
 **/
@Data
public class LoginAuthDTO {
    private String username;
    private String password;
}
