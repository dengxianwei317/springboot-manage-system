package com.spring.service.system.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.spring.enums.StatusCodeEnum;
import com.spring.constant.StringConstant;
import com.spring.dao.system.SysUserDao;
import com.spring.entity.common.Result;
import com.spring.entity.properties.TokenProperties;
import com.spring.entity.system.SystemUser;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.spring.service.system.SysUserService;
import com.spring.shiro.JwtToken;
import com.spring.utils.*;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;


/**
 * <p>
 * 角色
 * </p>
 *
 * @author dxw
 * @since 2020-08-29
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserDao, SystemUser> implements SysUserService {
    @Autowired
    TokenProperties tokenProperties;

    @Autowired
    SysUserDao userDao;

    /*
     * 登录验证
     * */
    @Override
    public Result login(SystemUser user) {
        Result result = null;
        try {
            QueryWrapper<SystemUser> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("account", user.getAccount());
            SystemUser systemUser = this.getOne(queryWrapper);

            String salt = systemUser.getSalt();
            // 得到 hash 后的密码
            String encodedPassword = ShiroUtils.getHashPassword(user.getPassword(), salt);

            if (!user.getAccount().equals(systemUser.getAccount()))
                result = ResultUtils.getResult(StatusCodeEnum._800, "账号错误", null);
            else if (!encodedPassword.equals(systemUser.getPassword()))
                result = ResultUtils.getResult(StatusCodeEnum._800, "密码错误", null);
            else if (!user.getAccount().equals(systemUser.getAccount())
                    && !encodedPassword.equals(systemUser.getPassword()))
                result = ResultUtils.getResult(StatusCodeEnum._800, "账号和密码错误", null);
            else {
                //登录成功则生成token
                String currentTimeMillis = String.valueOf(System.currentTimeMillis());
                String token = JwtUtils.createToken(systemUser, currentTimeMillis);

                Subject subject = SecurityUtils.getSubject();
                JwtToken jwtToken = new JwtToken(token);
                subject.login(jwtToken);

                if (subject.isAuthenticated()) {
                    //添加token时间戳
                    String refreshTokenKey = StringConstant.REFRESH_TOKEN + user.getAccount();
                    RedisUtils.set(refreshTokenKey, currentTimeMillis, tokenProperties.getTokenExpireTime() * 60);

                    result = ResultUtils.getResult(StatusCodeEnum._200, "登录成功", token);
                } else
                    result = ResultUtils.getResult(StatusCodeEnum._800, "token无效", null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Boolean add(SystemUser user) {
        Boolean isOK = false;
        try {
            if (checkAccount(user.getAccount()))
                return isOK;

            //新增的用户密码默认为：123
            user.setPassword("123");
            // 生成盐,默认长度 16 位
            String salt = ShiroUtils.getSalt();
            // 得到 hash 后的密码
            String encodedPassword = ShiroUtils.getHashPassword(user.getPassword(), salt);
            user.setId(UUID.randomUUID().toString().replaceAll("-", ""));
            user.setSalt(salt);
            user.setPassword(encodedPassword);
            user.setRegisterTime(DateUtils.getCurrentDatetime());

            if (this.save(user))
                isOK = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isOK;
    }

    /*
     * 修改账号,删除用户对应的token时间戳
     * */
    @Override
    public Boolean updateAccount(SystemUser user) {
        Boolean isOK = false;
        try {
            // 生成盐,默认长度 16 位
            String salt = ShiroUtils.getSalt();
            // 得到 hash 后的密码
            String encodedPassword = ShiroUtils.getHashPassword(user.getPassword(), salt);

            user.setSalt(salt);
            user.setPassword(encodedPassword);

            if (StringUtils.isEmpty(user.getUserName()))
                user.setUserName(null);
            if (StringUtils.isEmpty(user.getAccount()))
                user.setAccount(null);

            isOK = this.updateById(user);

            //有权限的角色修改其他角色，不需要删除，自己修改自己的账号必需要删除
            if (user.getId().equals(UserContext.getCurrentUser().getId())) {
                String refreshTokenKey = StringConstant.REFRESH_TOKEN + UserContext.getCurrentUser().getAccount();
                RedisUtils.delete(refreshTokenKey);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return isOK;
    }

    @Override
    public SystemUser getEntity(String userId) {
        SystemUser user = null;

        try {
            user = userDao.getEntity(userId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public IPage<SystemUser> getPageList(SystemUser user, Integer pageIndex, Integer pageSize) {
        IPage<SystemUser> list = null;
        QueryWrapper<SystemUser> queryWrapper = null;
        try {
            queryWrapper = new QueryWrapper<>();

            if (StringUtils.isNotEmpty(user.getIsEnabled()))
                queryWrapper.eq("is_enabled", user.getIsEnabled());

            queryWrapper.eq("is_delete", false).orderByDesc("register_time");

            if (StringUtils.isNotEmpty(user.getRegisterTime())) {
                String[] times = user.getRegisterTime().split(",");
                if (!times[0].equals("")) {
                    queryWrapper.ge("register_time", times[0]);//大于等于
                    queryWrapper.le("register_time", times[1]);//小于等于
                }
            }

            if (StringUtils.isNotEmpty(user.getSex()))
                queryWrapper.eq("sex", user.getSex());

            if (StringUtils.isNotEmpty(user.getUserName()))
                queryWrapper.like("user_name", user.getUserName());

            if (StringUtils.isNotEmpty(user.getAccount()))
                queryWrapper.like("account", user.getAccount());

            if (StringUtils.isNotEmpty(user.getPhone()))
                queryWrapper.like("phone", user.getPhone());

            IPage<SystemUser> page = new Page<>(pageIndex, pageSize);
            list = this.page(page, queryWrapper);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public IPage<SystemUser> getCheckedPageList(Map<String, Object> map, Integer pageIndex, Integer pageSize) {
        IPage<SystemUser> page = null;
        List<SystemUser> listUser = null;
        try {
            page = new Page<>(pageIndex, pageSize);
            listUser = userDao.getCheckedList(page, map);
            page.setRecords(listUser);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return page;
    }

    @Override
    public Boolean checkAccount(String account) {
        Boolean isOK = false;
        QueryWrapper<SystemUser> queryWrapper = null;

        try {
            queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("account", account);

            if (this.count(queryWrapper) > 0) {
                isOK = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isOK;
    }
}
