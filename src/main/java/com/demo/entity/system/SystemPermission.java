package com.demo.entity.system;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.demo.entity.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

//是lombok中最常用的注释了，它集合了@Getter和@Setter
@Data
//继承父类的字段和属性
@EqualsAndHashCode(callSuper = true)
//生成无参构造函数
@NoArgsConstructor
@TableName("system_permission")
public class SystemPermission extends BaseEntity {
    private String name;

    private String code;

    private String description;

    @TableField(value = "is_enabled", fill = FieldFill.INSERT)
    private String isEnabled;
}
