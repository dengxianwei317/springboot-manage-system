package com.demo.dao.system;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.demo.entity.system.SystemMenu;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 菜单配置
 * </p>
 *
 * @author dxw
 * @since 2020-08-29
 */
@Repository
public interface SysMenuDao extends BaseMapper<SystemMenu> {
    public List<SystemMenu> getListPermissionMenu(List<String> listRoleId);
}
