package com.spring.utils;

import com.spring.constant.StringConstant;
import com.spring.entity.system.SystemUser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import java.util.HashMap;
import java.util.Map;

/*
 * 上下文用户对象
 * */
public class UserContext {

    public static SystemUser getCurrentUser() {
        SystemUser user = null;
        Subject subject = SecurityUtils.getSubject();

        if (subject != null) {
            String token = String.valueOf(subject.getPrincipal());
            String cacheKey = StringConstant.CACHE_USER + JwtUtils.getClaim(token, StringConstant.ACCOUNT);
            user = (SystemUser) RedisUtils.get(cacheKey);
        }
        return user;
    }
}
