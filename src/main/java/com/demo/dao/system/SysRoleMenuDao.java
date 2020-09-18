package com.demo.dao.system;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.demo.entity.system.SystemRoleMenu;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * <p>
 * 角色和菜单配置
 * </p>
 *
 * @author dxw
 * @since 2020-08-29
 */

@Repository
public interface SysRoleMenuDao extends BaseMapper<SystemRoleMenu> {
    public int batchDelete(Map<String, Object> map);
}
