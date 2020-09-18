package com.demo.service.system;

import com.baomidou.mybatisplus.extension.service.IService;
import com.demo.entity.system.SystemRolePermission;

import java.util.List;

public interface SysRolePermissionService extends IService<SystemRolePermission> {
    public Boolean batchAdd(List<String> listPermissionId, String roleId);

    public Boolean batchDelete(String roleId, List<String> listPermissionId);

    public List<String> getCheckedPermissionIds(String roleId);
}
