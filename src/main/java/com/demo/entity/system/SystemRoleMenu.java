package com.demo.entity.system;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.demo.entity.base.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 角色和菜单关联表
 * </p>
 *
 * @author dxw
 * @since 2020-08-29
 */

//是lombok中最常用的注释了，它集合了@Getter和@Setter
@Data
//生成无参构造函数
@NoArgsConstructor
@TableName("system_role_menu")
public class SystemRoleMenu {
    @TableId
    private String id;

    @TableField("role_id")
    private String roleId;

    @TableField("menu_id")
    private String menuId;
}
