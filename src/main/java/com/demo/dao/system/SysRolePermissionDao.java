package com.demo.dao.system;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.demo.entity.system.SystemRolePermission;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/*
* 接口权限
* */

@Repository
public interface SysRolePermissionDao extends BaseMapper<SystemRolePermission> {
    public int batchDelete(Map<String, Object> map);

    public List<String> getCheckedPermissionIds(String roleId);
}
