package com.spring.entity.system;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.spring.entity.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * <p>
 * 菜单表
 * </p>
 *
 * @author dxw
 * @since 2020-08-29
 */
//是lombok中最常用的注释了，它集合了@Getter和@Setter
@Data
//继承父类的字段和属性
@EqualsAndHashCode(callSuper = true)
//生成无参构造函数
@NoArgsConstructor
@TableName("system_menu")
public class SystemMenu extends BaseEntity {
    //vue路由路径
    @TableField("route_path")
    private String routePath;

    //菜单名称
    private String name;

    //菜单图标class
    @TableField("icon_class")
    private String iconClass;

    //vue组件路径
    @TableField("component_path")
    private String componentPath;

    //上级菜单id
    @TableField("parent_id")
    private String parentId;

    //上级菜单名称
    @TableField("parent_name")
    private String parentName;

    //菜单层级关联id 拼接：xxx.xxx.xxx
    @TableField("menu_code")
    private String menuCode;

    //菜单排序
    private Integer sort;

    //路由是否需要认证
    @TableField(value = "is_require_auth")
    private String isRequireAuth;

    //是否启用
    @TableField(value = "is_enabled", fill = FieldFill.INSERT)
    private String IsEnabled;

    //exist = false，不需要映射到数据库表字段
    @TableField(exist = false)
    private List<SystemMenu> child;

    //菜单页面配置的按钮，exist = false，不需要映射到数据库表字段
    @TableField(exist = false)
    private List<SystemButton> listButton;
}
