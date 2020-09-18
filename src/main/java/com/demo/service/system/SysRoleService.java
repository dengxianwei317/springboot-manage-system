package com.demo.service.system;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.demo.entity.system.SystemRole;

import java.util.List;

/**
 * <p>
 *  角色
 * </p>
 *
 * @author dxw
 * @since 2020-08-29
 */
public interface SysRoleService extends IService<SystemRole>{
    public Boolean add(SystemRole role);

    public Boolean checkRoleName(String name);

    public IPage<SystemRole> getPageList(SystemRole role, Integer pageIndex, Integer pageSize);
}
