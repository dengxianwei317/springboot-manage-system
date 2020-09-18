package com.demo.service.system;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.demo.entity.common.Result;
import com.demo.entity.system.SystemUser;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户
 * </p>
 *
 * @author dxw
 * @since 2020-08-29
 */

public interface SysUserService extends IService<SystemUser>{
    public Result login(SystemUser user);

    public Boolean add(SystemUser user);

    public Boolean updateAccount(SystemUser user);

    public Boolean checkAccount(String account);

    public SystemUser getEntity(String userId);

    public IPage<SystemUser> getPageList(SystemUser user, Integer pageIndex, Integer pageSize);

    public IPage<SystemUser> getCheckedPageList(Map<String, Object> map, Integer pageIndex, Integer pageSize);
}
