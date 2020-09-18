package com.demo.entity.base;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

import java.io.Serializable;

//是lombok中最常用的注释了，它集合了@Getter和@Setter
@Data
public class BaseEntity implements Serializable {
    @TableId
    private String id;

    //插入自动填充数据
    @TableField(value = "is_delete", fill = FieldFill.INSERT)
    @TableLogic
    private Boolean IsDelete;

    @TableField(value = "create_id", fill = FieldFill.INSERT)
    private String createId;

    @TableField(value = "create_name", fill = FieldFill.INSERT)
    private String createName;

    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private String createTime;
    //修改自动填充数据
    @TableField(value = "update_id", fill = FieldFill.UPDATE)
    private String updateId;

    @TableField(value = "update_name", fill = FieldFill.UPDATE)
    private String updateName;

    @TableField(value = "update_time", fill = FieldFill.UPDATE)
    private String updateTime;
}
