package com.spring.service.system.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.spring.dao.system.SysRoleMenuDao;
import com.spring.entity.system.SystemRoleMenu;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.spring.service.system.SysRoleMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <p>
 * 角色和菜单
 * </p>
 *
 * @author dxw
 * @since 2020-08-29
 */
@Service
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuDao, SystemRoleMenu> implements SysRoleMenuService {

    @Autowired
    SysRoleMenuDao roleMenuDao;

    @Override
    public List<SystemRoleMenu> getList(String roleId) {
        List<SystemRoleMenu> listRoleMenu = null;
        QueryWrapper<SystemRoleMenu> queryWrapper = null;

        try {
            queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("role_id", roleId);
            listRoleMenu = this.list(queryWrapper);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listRoleMenu;
    }

    @Override
    public Boolean batchAdd(List<String> listMenuId, String roleId) {
        Boolean isOK = false;
        try {
            List<SystemRoleMenu> listRoleMenu = new LinkedList<>();
            SystemRoleMenu roleMenu = null;

            for (String menuId : listMenuId) {
                roleMenu = new SystemRoleMenu();
                roleMenu.setId(UUID.randomUUID().toString().replace("-", ""));
                roleMenu.setRoleId(roleId);
                roleMenu.setMenuId(menuId);
                listRoleMenu.add(roleMenu);
            }

            isOK = this.saveBatch(listRoleMenu);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isOK;
    }

    @Override
    public Boolean batchDelete(List<String> listMenuId, String roleId) {
        int count = 0;
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("listMenuId", listMenuId);
            map.put("roleId", roleId);

            count = roleMenuDao.batchDelete(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count > 0;
    }
}
