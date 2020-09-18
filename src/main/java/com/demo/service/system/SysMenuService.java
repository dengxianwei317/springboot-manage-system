package com.demo.service.system;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.demo.entity.system.SystemMenu;

import java.util.List;

/**
 * <p>
 * 菜单
 * </p>
 *
 * @author dxw
 * @since 2020-08-29
 */
public interface SysMenuService extends IService<SystemMenu>{
    public Boolean add(SystemMenu menu);

    public Boolean checkRoutePath(String routePath);

    public IPage<SystemMenu> getPageList(SystemMenu menu, Integer pageIndex, Integer pageSize);

    public List<SystemMenu> getListTreeMenu();

    public List<SystemMenu> getListPermissionMenu(List<String> listRoleId);
}
