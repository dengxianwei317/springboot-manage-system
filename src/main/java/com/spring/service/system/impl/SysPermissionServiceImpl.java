package com.spring.service.system.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.spring.dao.system.SysPermissionDao;
import com.spring.entity.system.SystemPermission;
import com.spring.service.system.SysPermissionService;
import com.spring.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/*
 * 接口权限
 * */

@Service
public class SysPermissionServiceImpl extends ServiceImpl<SysPermissionDao, SystemPermission> implements SysPermissionService {

    @Autowired
    SysPermissionDao permissionDao;

    @Override
    public Boolean add(SystemPermission entity) {
        Boolean isOK = false;

        try {
            if (checkPermission(entity))
                return isOK;

            if (StringUtils.isEmpty(entity.getId()))
                entity.setId(UUID.randomUUID().toString().replaceAll("-", ""));

            isOK = this.save(entity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isOK;
    }

    @Override
    public Boolean checkPermission(SystemPermission entity) {
        int count = 0;
        QueryWrapper<SystemPermission> queryWrapper = null;
        try {
            queryWrapper = new QueryWrapper<>();

            if (StringUtils.isNotEmpty(entity.getName()))
                queryWrapper.eq("name", entity.getName());

            if (StringUtils.isNotEmpty(entity.getCode()))
                queryWrapper.eq("code", entity.getCode());

            count = this.count(queryWrapper);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count > 0;
    }

    /*
     * 获取角色分配的接口权限编码
     * */
    @Override
    public List<String> getListCode(String roleId) {
        List<String> listCode = null;

        try {
            listCode = permissionDao.getListCode(roleId);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listCode;
    }

    /*
     * 获取角色已分配的接口权限
     * */
    @Override
    public IPage<SystemPermission> getCheckedPageList(Map<String, Object> map, Integer pageIndex, Integer pageSize) {
        IPage<SystemPermission> page = null;
        List<SystemPermission> listData = null;
        try {
            page = new Page<>(pageIndex, pageSize);
            listData = permissionDao.getCheckedList(page, map);
            page.setRecords(listData);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return page;
    }

    @Override
    public IPage<SystemPermission> getPageList(SystemPermission entity, Integer pageIndex, Integer pageSize) {
        IPage<SystemPermission> list = null;
        QueryWrapper<SystemPermission> queryWrapper = null;
        try {
            queryWrapper = new QueryWrapper<>();

            if (StringUtils.isNotEmpty(entity.getIsEnabled()))
                queryWrapper.eq("is_enabled", entity.getIsEnabled());

            if (StringUtils.isNotEmpty(entity.getName()))
                queryWrapper.like("name", entity.getName());

            queryWrapper.eq("is_delete", false).orderByDesc("create_time");

            IPage<SystemPermission> page = new Page<>(pageIndex, pageSize);
            list = this.page(page, queryWrapper);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
