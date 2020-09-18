package com.spring.entity.system;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.spring.entity.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 角色表
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
@TableName("system_role")
public class SystemRole extends BaseEntity {
    //角色英文名称
    @TableField("role_name")
    private String roleName;

    //角色中文名称
    @TableField("role_name_zh")
    private String roleNameZh;

    //是否可以被继承
    @TableField("is_inherit")
    private String isInherit;

    //角色描述
    private String description;

    //是否启用
    @TableField(value = "is_enabled", fill = FieldFill.INSERT)
    private String IsEnabled;
}
