package com.spring.service.system.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.spring.dao.system.SysMenuDao;
import com.spring.entity.system.SystemMenu;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.spring.service.system.SysMenuService;
import com.spring.utils.StringUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

/**
 * <p>
 * 菜单
 * </p>
 *
 * @author dxw
 * @since 2020-08-29
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuDao, SystemMenu> implements SysMenuService {

    @Autowired
    SysMenuDao menuDao;

    @Override
    public Boolean add(SystemMenu menu) {
        Boolean isOK = false;
        String menuCode;

        try {
            if (checkRoutePath(menu.getRoutePath()))
                return isOK;

            if (StringUtils.isEmpty(menu.getId()))
                menu.setId(UUID.randomUUID().toString().replaceAll("-", ""));

            if (StringUtils.isEmpty(menu.getMenuCode()))
                menuCode = menu.getId();
            else
                menuCode = menu.getMenuCode() + "." + menu.getId();
            menu.setMenuCode(menuCode);
            isOK = this.save(menu);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isOK;
    }

    @Override
    public IPage<SystemMenu> getPageList(SystemMenu menu, Integer pageIndex, Integer pageSize) {
        IPage<SystemMenu> list = null;
        QueryWrapper<SystemMenu> queryWrapper = null;
        try {
            queryWrapper = new QueryWrapper<>();

            queryWrapper.eq("is_delete", false).orderByDesc("sort");

            if (StringUtils.isNotEmpty(menu.getIsEnabled()))
                queryWrapper.eq("is_enabled", menu.getIsEnabled());

            if (StringUtils.isNotEmpty(menu.getName()))
                queryWrapper.like("name", menu.getName());

            IPage<SystemMenu> page = new Page<>(pageIndex, pageSize);
            list = this.page(page, queryWrapper);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /*
     * 检查路由地址唯一
     * */
    @Override
    public Boolean checkRoutePath(String routePath) {
        int count = 0;
        QueryWrapper<SystemMenu> queryWrapper = null;
        try {
            queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("route_path", routePath);
            count = this.count(queryWrapper);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count > 0;
    }

    /*
     * 获取层级菜单
     * */
    @Override
    public List<SystemMenu> getListTreeMenu() {
        List<SystemMenu> listMenu = null;
        try {
            listMenu = new LinkedList<>();
            QueryWrapper<SystemMenu> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("is_delete", false).eq("is_enabled", "1")
                    .orderByAsc("sort");

            listMenu = this.list(queryWrapper);
            listMenu = getTreeMenu(listMenu);

            listMenu = (List<SystemMenu>) CollectionUtils.select(listMenu, new Predicate() {
                @Override
                public boolean evaluate(Object o) {
                    SystemMenu menu = (SystemMenu) o;
                    return menu.getParentId().equals("");
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listMenu;
    }

    /*
    * 获取角色已分配的菜单
    * */
    @Override
    public List<SystemMenu> getListPermissionMenu(List<String> listRoleId) {
        List<SystemMenu> listMenu = null;

        try {
            listMenu = menuDao.getListPermissionMenu(listRoleId);
            listMenu = getTreeMenu(listMenu);

            listMenu = (List<SystemMenu>) CollectionUtils.select(listMenu, new Predicate() {
                @Override
                public boolean evaluate(Object o) {
                    SystemMenu menu = (SystemMenu) o;
                    return menu.getParentId().equals("");
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listMenu;
    }

    private List<SystemMenu> getTreeMenu(List<SystemMenu> listMenu) {
        List<SystemMenu> listChild = null;

        try {
            for (SystemMenu menu : listMenu) {
                listChild = (List<SystemMenu>) CollectionUtils.select(listMenu, new Predicate() {
                    @Override
                    public boolean evaluate(Object o) {
                        SystemMenu entity = (SystemMenu) o;
                        return menu.getId().equals(entity.getParentId());
                    }
                });

                if (listChild.size() > 0) {
                    for (SystemMenu item : listChild) {
                        int index = item.getRoutePath().indexOf("/");
                        if (index >= 0) {
                            item.setRoutePath(item.getRoutePath().substring(index + 1));
                        }
                    }

                    menu.setChild(listChild);

                    getTreeMenu(listChild);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listMenu;
    }
}
