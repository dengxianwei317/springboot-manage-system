package com.spring.entity.file;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.spring.entity.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 图片表
 * </p>
 *
 * @author dxw
 * @since 2020-08-29
 */

//是lombok中最常用的注释了，它集合了@Getter和@Setter
@Data
//对象有继承了父类方法和属性
@EqualsAndHashCode(callSuper = true)
@TableName("file_image")
public class FileImage extends BaseEntity {

    @TableField("file_name")
    private String fileName;

    @TableField("file_path")
    private String filePath;

    @TableField("file_original_name")
    private String fileOriginalName;

    @TableField("file_size")
    private Integer fileSize;
}
