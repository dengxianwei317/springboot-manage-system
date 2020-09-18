package com.spring.service.system.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.spring.dao.system.SysRoleMenuButtonDao;
import com.spring.entity.system.SystemRoleMenuButton;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.spring.service.system.SysRoleMenuButtonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * <p>
 * 菜单和按钮
 * </p>
 *
 * @author dxw
 * @since 2020-08-29
 */
@Service
public class SysRoleMenuButtonServiceImpl extends ServiceImpl<SysRoleMenuButtonDao, SystemRoleMenuButton> implements SysRoleMenuButtonService {

    @Autowired
    SysRoleMenuButtonDao buttonMenuDao;

    @Override
    public List<SystemRoleMenuButton> getCheckedList(String roleId, String menuId) {
        List<SystemRoleMenuButton> listButtonMenu = null;
        QueryWrapper<SystemRoleMenuButton> queryWrapper = null;

        try {
            queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("menu_id", menuId).eq("role_id", roleId);
            listButtonMenu = this.list(queryWrapper);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listButtonMenu;
    }

    @Override
    public Boolean batchAddAndDelete(Map<String, Object> params) {
        Boolean isOK = false;

        try {
            List<Map<String, Object>> listAdd = (List<Map<String, Object>>) params.get("listAdd");
            List<Map<String, Object>> listDelete = (List<Map<String, Object>>) params.get("listDelete");

            if (listAdd.size() > 0)
                isOK = saveBatch(listAdd);

            if (listDelete.size() > 0)
                isOK = deleteBatch(listDelete);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return isOK;
    }

    private Boolean saveBatch(List<Map<String, Object>> listAdd) {
        Boolean isOK = false;
        List<SystemRoleMenuButton> listData = new ArrayList<>();
        SystemRoleMenuButton entity = null;

        try {
            for (Map<String, Object> map : listAdd) {
                String roleId = (String) map.get("roleId");
                String menuId = (String) map.get("menuId");
                List<String> listButtonId = (List<String>) map.get("buttonIds");

                for (String buttonId : listButtonId) {
                    entity = new SystemRoleMenuButton();
                    entity.setId(UUID.randomUUID().toString().replace("-", ""));
                    entity.setRoleId(roleId);
                    entity.setMenuId(menuId);
                    entity.setButtonId(buttonId);

                    listData.add(entity);
                }
            }

            isOK = this.saveBatch(listData);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isOK;
    }

    private Boolean deleteBatch(List<Map<String, Object>> listDelete) {
        int count = 0;

        try {
            for (Map<String, Object> map : listDelete) {
                count = buttonMenuDao.batchDelete(map);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return count > 0;
    }
}
