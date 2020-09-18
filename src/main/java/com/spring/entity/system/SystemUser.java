package com.spring.entity.system;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.spring.entity.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

import java.util.List;

/**
 * <p>
 * 用户表
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
@TableName("system_user")
public class SystemUser extends BaseEntity {
    @TableField("user_name")
    private String userName;
@FieldNameConstants.Include
    private String account;

    private String password;

    private String sex;

    private String phone;

    private String email;

    private String birthday;

    //注册时间
    @TableField("register_time")
    private String registerTime;

    //加密盐
    private String salt;

    //是否启用
    @TableField(value = "is_enabled", fill = FieldFill.INSERT)
    private String IsEnabled;

    //用户包含的角色，exist = false，不映射到数据库字段
    @TableField(exist = false)
    private List<SystemRole> listRole;
}
