package com.spring.service.system;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.spring.entity.system.SystemPermission;

import java.util.List;
import java.util.Map;

/*
* 接口权限
* */

public interface SysPermissionService extends IService<SystemPermission>{
    public Boolean add(SystemPermission entity);

    public Boolean checkPermission(SystemPermission entity);

    public List<String> getListCode(String roleId);

    public IPage<SystemPermission> getCheckedPageList(Map<String, Object> map, Integer pageIndex, Integer pageSize);

    public IPage<SystemPermission> getPageList(SystemPermission button, Integer pageIndex, Integer pageSize);
}
