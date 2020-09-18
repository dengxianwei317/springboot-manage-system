package com.demo.service.system;

import com.baomidou.mybatisplus.extension.service.IService;
import com.demo.entity.system.SystemRoleMenuButton;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 菜单和按钮
 * </p>
 *
 * @author dxw
 * @since 2020-08-29
 */
public interface SysRoleMenuButtonService extends IService<SystemRoleMenuButton> {
    public List<SystemRoleMenuButton> getCheckedList(String roleId, String menuId);

    public Boolean batchAddAndDelete(Map<String, Object> params);
}
