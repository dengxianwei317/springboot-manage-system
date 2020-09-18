package com.spring.service.system;

import com.baomidou.mybatisplus.extension.service.IService;
import com.spring.entity.system.SystemUserRole;

import java.util.List;

/**
 * <p>
 * 用户和角色
 * </p>
 *
 * @author dxw
 * @since 2020-08-29
 */
public interface SysUserRoleService extends IService<SystemUserRole>{
    public Boolean batchAdd(List<String> listUserId, String roleId);

    public Boolean batchDelete(String roleId, List<String> listUserId);

    public List<String> getCheckedUserIds(String roleId);
}
