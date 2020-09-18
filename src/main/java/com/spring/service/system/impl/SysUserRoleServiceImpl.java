package com.spring.service.system.impl;

import com.spring.constant.StringConstant;
import com.spring.dao.system.SysUserRoleDao;
import com.spring.entity.system.SystemUser;
import com.spring.entity.system.SystemUserRole;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.spring.service.system.SysUserRoleService;
import com.spring.service.system.SysUserService;
import com.spring.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <p>
 * 用户和角色
 * </p>
 *
 * @author dxw
 * @since 2020-08-29
 */
@Service
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleDao, SystemUserRole> implements SysUserRoleService {

    @Autowired
    SysUserRoleDao userRoleDao;
    @Autowired
    SysUserService userService;

    /*
    * 添加用户角色权限
    * */
    @Override
    public Boolean batchAdd(List<String> listUserId, String roleId) {
        Boolean isOK = false;
        List<SystemUserRole> listUserRole = null;
        SystemUserRole userRole = null;
        try {
            listUserRole = new LinkedList<>();
            for (String userId : listUserId) {
                userRole = new SystemUserRole();
                userRole.setId(UUID.randomUUID().toString().replace("-", ""));
                userRole.setRoleId(roleId);
                userRole.setUserId(userId);
                listUserRole.add(userRole);

                //redis缓存的用户数据包含角色信息，需删除
                SystemUser user = userService.getById(userId);
                String cacheKey = StringConstant.CACHE_USER + user.getAccount();
                if (RedisUtils.exists(cacheKey))
                    RedisUtils.delete(cacheKey);
            }
            isOK = this.saveBatch(listUserRole);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return isOK;
    }

    /*
    * 删除用户角色权限
    * */
    @Override
    public Boolean batchDelete(String roleId, List<String> listUserId) {
        int count = 0;
        Map<String, Object> map = null;

        try {
            map = new HashMap<>();
            map.put("roleId", roleId);
            map.put("listUserId", listUserId);

            for (String userId : listUserId) {
                //redis缓存的用户数据包含角色信息，需删除
                SystemUser user = userService.getById(userId);
                String cacheKey = StringConstant.CACHE_USER + user.getAccount();
                if (RedisUtils.exists(cacheKey))
                    RedisUtils.delete(cacheKey);
            }

            count = userRoleDao.batchDelete(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count > 0;
    }

    @Override
    public List<String> getCheckedUserIds(String roleId) {
        List<String> listUserId = null;

        try {
            listUserId = userRoleDao.getCheckedUserIds(roleId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listUserId;
    }
}
