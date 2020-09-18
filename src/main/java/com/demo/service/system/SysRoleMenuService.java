package com.demo.service.system;

import com.baomidou.mybatisplus.extension.service.IService;
import com.demo.entity.system.SystemRoleMenu;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 角色和菜单
 * </p>
 *
 * @author dxw
 * @since 2020-08-29
 */
public interface SysRoleMenuService extends IService<SystemRoleMenu>{
    public List<SystemRoleMenu> getList(String roleId);

    public Boolean batchAdd(List<String> listMenuId, String roleId);

    public Boolean batchDelete(List<String> listMenuId, String roleId);
}
