package cn.poolify.admin.trigger.http.controller.web;

import cn.poolify.admin.trigger.http.api.dto.auth.LoginAuthDTO;
import cn.poolify.admin.trigger.http.utils.AuthUtils;
import cn.poolify.admin.trigger.http.utils.ReturnUtil;
import cn.poolify.admin.trigger.http.api.vo.auth.CheckAuthVO;
import cn.poolify.admin.trigger.http.api.vo.auth.LoginAuthVO;
import cn.poolify.common.exception.DynamicThreadPoolException;
import cn.poolify.common.exception.ErrorCode;
import cn.poolify.common.response.HttpResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * @Author: HCJ
 * @DateTime: 2024/9/19
 * @Description:
 **/
@Slf4j
@RestController
@RequestMapping("/auth")
@CrossOrigin(allowCredentials = "true", originPatterns = {"http://localhost*", "http://127.0.0.1*"})
public class AuthController {

    @PostMapping("/login")
    public HttpResponse<LoginAuthVO> login(@RequestBody LoginAuthDTO loginAuthDTO, HttpServletRequest request){
        String username = loginAuthDTO.getUsername();
        String password = loginAuthDTO.getPassword();

        if (!Objects.equals(username, "admin") ||
                !Objects.equals(password, "admin")) {
            throw new DynamicThreadPoolException(ErrorCode.LOGIN_FAIL);
        }

        String token = AuthUtils.generateToken();
        request.getSession().setAttribute(AuthUtils.SESSION_AUTH_KEY, token);
        return ReturnUtil.success(new LoginAuthVO(token));
    }

    @GetMapping("/check")
    public HttpResponse<CheckAuthVO> checkAuth(HttpServletRequest request) {
        return ReturnUtil.success(new CheckAuthVO(AuthUtils.hashAuth(request)));
    }
}
