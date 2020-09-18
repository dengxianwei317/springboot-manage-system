package com.demo.dao.system;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.demo.entity.system.SystemRoleMenuButton;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * <p>
 * 菜单页面和按钮配置
 * </p>
 *
 * @author dxw
 * @since 2020-08-29
 */

@Repository
public interface SysRoleMenuButtonDao extends BaseMapper<SystemRoleMenuButton> {
    public int batchDelete(Map<String, Object> map);
}
