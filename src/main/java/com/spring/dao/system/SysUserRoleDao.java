package com.spring.dao.system;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.spring.entity.system.SystemUserRole;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author dxw
 * @since 2020-08-29
 */

@Repository
public interface SysUserRoleDao extends BaseMapper<SystemUserRole> {
    public List<String> getCheckedUserIds(String role);

    public int batchDelete(Map<String, Object> params);
}
