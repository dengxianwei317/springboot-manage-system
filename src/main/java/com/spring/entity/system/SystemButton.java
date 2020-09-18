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
 * 按钮表
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
@TableName("system_button")
public class SystemButton extends BaseEntity {
    //按钮名称
    @TableField("button_name")
    private String buttonName;

    //点击事件名称
    @TableField("click_event")
    private String clickEvent;

    //按钮类型(1：工具栏，2:表格行内)
    @TableField("button_type")
    private String buttonType;

    //按钮图标class
    @TableField("icon_class")
    private String iconClass;

    //按钮css样式
    @TableField("css_class")
    private String cssClass;

    //序号
    private Integer sort;

    //是否启用
    @TableField(value = "is_enabled", fill = FieldFill.INSERT)
    private String IsEnabled;
}
