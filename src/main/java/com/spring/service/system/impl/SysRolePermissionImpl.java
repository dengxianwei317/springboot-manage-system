package com.spring.service.system.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.spring.dao.system.SysRolePermissionDao;
import com.spring.entity.system.SystemRolePermission;
import com.spring.service.system.SysRolePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SysRolePermissionImpl extends ServiceImpl<SysRolePermissionDao, SystemRolePermission> implements SysRolePermissionService {

    @Autowired
    SysRolePermissionDao rolePermissionDao;

    @Override
    public Boolean batchAdd(List<String> listPermissionId, String roleId) {
        Boolean isOK = false;
        List<SystemRolePermission> listData = null;
        SystemRolePermission rolePermission = null;
        try {
            listData = new LinkedList<>();
            for (String permissionId : listPermissionId) {
                rolePermission = new SystemRolePermission();
                rolePermission.setId(UUID.randomUUID().toString().replace("-", ""));
                rolePermission.setRoleId(roleId);
                rolePermission.setPermissionId(permissionId);
                listData.add(rolePermission);
            }
            isOK = this.saveBatch(listData);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return isOK;
    }

    @Override
    public Boolean batchDelete(String roleId, List<String> listPermissionId) {
        int count = 0;
        Map<String, Object> map = null;

        try {
            map = new HashMap<>();
            map.put("roleId", roleId);
            map.put("listPermissionId", listPermissionId);

            count = rolePermissionDao.batchDelete(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count > 0;
    }

    @Override
    public List<String> getCheckedPermissionIds(String roleId) {
        List<String> listPermissionId = null;

        try {
            listPermissionId = rolePermissionDao.getCheckedPermissionIds(roleId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listPermissionId;
    }
}
