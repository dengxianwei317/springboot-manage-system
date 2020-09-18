package com.spring.service.system.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.spring.dao.system.SysRoleDao;
import com.spring.entity.properties.TokenProperties;
import com.spring.entity.system.SystemRole;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.spring.service.system.SysRoleService;
import com.spring.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
public class SysRoleServiceImpl extends ServiceImpl<SysRoleDao, SystemRole> implements SysRoleService {

    @Autowired
    TokenProperties tokenProperties;

    @Autowired
    SysRoleDao roleDao;

    @Override
    public Boolean add(SystemRole role) {
        Boolean isOK = false;
        try {
            if (checkRoleName(role.getRoleName()))
                return isOK;

            if (role.getId() == null || role.getId().equals(""))
                role.setId(UUID.randomUUID().toString().replaceAll("-", ""));

            isOK = this.save(role);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isOK;
    }

    @Override
    public Boolean checkRoleName(String name) {
        int count = 0;
        QueryWrapper<SystemRole> queryWrapper = null;
        try {
            queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("role_name", name);
            count = this.count(queryWrapper);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count > 0;
    }

    @Override
    public IPage<SystemRole> getPageList(SystemRole role, Integer pageIndex, Integer pageSize) {
        IPage<SystemRole> list = null;
        QueryWrapper<SystemRole> queryWrapper = null;
        try {
            queryWrapper = new QueryWrapper<>();

            queryWrapper.eq("is_delete", false).orderByDesc("create_time");

            if (StringUtils.isNotEmpty(role.getIsEnabled()))
                queryWrapper.eq("is_enabled", role.getIsEnabled());

            if (StringUtils.isNotEmpty(role.getRoleName()))
                queryWrapper.like("role_name", role.getRoleName());

            IPage<SystemRole> page = new Page<>(pageIndex, pageSize);
            list = this.page(page, queryWrapper);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
