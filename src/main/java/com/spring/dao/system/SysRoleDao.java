package com.spring.dao.system;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.spring.entity.system.SystemRole;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 角色配置
 * </p>
 *
 * @author dxw
 * @since 2020-08-29
 */

@Repository
public interface SysRoleDao extends BaseMapper<SystemRole> {

}
