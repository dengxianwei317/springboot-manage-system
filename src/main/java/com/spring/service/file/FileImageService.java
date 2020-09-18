package com.spring.service.file;

import com.baomidou.mybatisplus.extension.service.IService;
import com.spring.entity.common.Result;
import com.spring.entity.file.FileImage;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * <p>
 *  图片
 * </p>
 *
 * @author dxw
 * @since 2020-08-29
 */
public interface FileImageService extends IService<FileImage> {
    public Result upload(MultipartFile[] files);

    public Result getPageListFile(Map<Object, Object> params);
}
