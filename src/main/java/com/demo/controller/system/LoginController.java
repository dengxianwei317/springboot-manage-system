package com.demo.controller.system;

import com.demo.enums.StatusCodeEnum;
import com.demo.constant.StringConstant;
import com.demo.entity.common.Result;
import com.demo.entity.properties.TokenProperties;
import com.demo.entity.system.SystemUser;
import com.demo.service.system.SysUserService;
import com.demo.utils.*;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("api")
public class LoginController {

    @Autowired
    TokenProperties tokenProperties;
    @Autowired
    SysUserService userService;

    @PostMapping(value = "/login")
    @ResponseBody
    public Result login(SystemUser user) {
        Result result = null;
        Map<String, Object> map = new HashMap<>();
        try {
            result = userService.login(user);
            if (result.getCode() == 200) {
                String token = String.valueOf(result.getData());
                map.put("token", token);
                map.put("expireTime", tokenProperties.getTokenExpireTime());

                user.setId(JwtUtils.getClaim(token, StringConstant.USER_ID));
                user.setAccount(JwtUtils.getClaim(token, StringConstant.ACCOUNT));
                user.setUserName(JwtUtils.getClaim(token, StringConstant.USER_NAME));
                map.put("user", user);
                result = ResultUtils.getResult(StatusCodeEnum._200, "登录成功", map);
            } else
                result = ResultUtils.getResult(StatusCodeEnum._800, "账号或密码不正确", null);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @PostMapping(value = "/logout")
    @ResponseBody
    public Result logout(String account) {
        Result result = null;
        Subject subject = null;
        try {
            subject = SecurityUtils.getSubject();
            subject.logout();
            result = ResultUtils.getResult(StatusCodeEnum._200, "登出成功", null);
            RedisUtils.delete(StringConstant.REFRESH_TOKEN + account);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    @GetMapping(value = "/authentication")
    @ResponseBody
    public SystemUser authentication() {
        SystemUser user = null;
        try {
            user = new SystemUser();
            user.setId(UserContext.getCurrentUser().getId());
            user.setAccount(UserContext.getCurrentUser().getAccount());
            user.setUserName(UserContext.getCurrentUser().getUserName());
            user.setListRole(UserContext.getCurrentUser().getListRole());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }
}
