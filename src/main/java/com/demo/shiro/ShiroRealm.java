package com.demo.shiro;


import com.demo.entity.system.SystemRole;
import com.demo.service.system.SysPermissionService;
import com.demo.service.system.SysUserService;
import com.demo.utils.JwtUtils;
import com.demo.utils.ShiroUtils;
import com.demo.utils.UserContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/*
 * 安全授权
 * */
@Component
@Slf4j
public class ShiroRealm extends AuthorizingRealm {

    @Autowired
    SysUserService userService;

    @Autowired
    SysPermissionService permissionService;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    /**
     * 只有当需要检测用户权限的时候才会调用此方法，例如checkRole,checkPermission之类的
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) throws AuthorizationException {
        SimpleAuthorizationInfo auth = new SimpleAuthorizationInfo();

        List<SystemRole> listRole = UserContext.getCurrentUser().getListRole();

        if (listRole == null)
            throw new AuthorizationException("未分配角色，无访问权限");

        Set<String> setPermission = new HashSet<>();

        for (SystemRole role : listRole) {
            auth.addRole(role.getRoleName());
            List<String> listCode = permissionService.getListCode(role.getId());
            setPermission.addAll(listCode);
        }

        auth.addStringPermissions(setPermission);

        return auth;
    }

    /*
     * 使用此方法进行用户登录验证。
     * */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken auth) throws AuthenticationException {
        String token = (String) auth.getPrincipal();
        Boolean isValid = JwtUtils.checkToken(token);

        if (!isValid)
            new AuthenticationException("token无效");

        String salt = ShiroUtils.getSalt();
        String tokenHash = ShiroUtils.getHashPassword(token, salt);

        return new SimpleAuthenticationInfo(token, tokenHash, ByteSource.Util.bytes(salt), getName());
    }
}
