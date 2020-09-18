package com.spring.service.file.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.spring.enums.StatusCodeEnum;
import com.spring.dao.file.FileImageDao;
import com.spring.entity.common.Result;
import com.spring.entity.file.FileImage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.spring.service.file.FileImageService;
import com.spring.utils.DateUtils;
import com.spring.utils.ResultUtils;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 图片
 * </p>
 *
 * @author dxw
 * @since 2020-08-29
 */
@Service
public class FileImageServiceImpl extends ServiceImpl<FileImageDao, FileImage> implements FileImageService {

    @Override
    public Result upload(MultipartFile[] files) {
        Result result = null;
        try {
            if (files.length < 1) {
                // 设置错误状态码
                result = ResultUtils.getResult(StatusCodeEnum._800, "请选择文件！", null);
                return result;
            }

            String originalFilename;
            String extensionName;
            String path;
            String toPath = "F:/resources/image/";
            File file;
            List<FileImage> listImage = new ArrayList<>();

            for (MultipartFile imgFile : files) {
                originalFilename = imgFile.getOriginalFilename();
                extensionName = originalFilename.substring(originalFilename.indexOf("."));
                // 存放上传图片的文件
                file = getFileDir(toPath, extensionName);

                System.out.println(file.getAbsolutePath());
                // 上传图片到 -》 “绝对路径”
                FileUtils.copyInputStreamToFile(imgFile.getInputStream(), file);

                FileImage image = new FileImage();
                image.setFileName(file.getName());
                image.setFileOriginalName(originalFilename);
                int index = file.getPath().indexOf("\\");
                path = file.getPath().substring(index + 1);
                index = path.indexOf("\\");
                path = path.substring(index + 1);
                image.setFilePath(path);
                image.setFileSize((int) imgFile.getSize());
                listImage.add(image);
            }
            Boolean isOK = this.saveBatch(listImage);
            if (isOK)
                result = ResultUtils.getResult(StatusCodeEnum._200, "上传成功", listImage);
            else
                result = ResultUtils.getResult(StatusCodeEnum._800, "上传失败", null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Result getPageListFile(Map<Object, Object> params) {
        Result result = null;
        try {
            Integer pageNum = Integer.parseInt(params.get("pageNum") == null ? "1" : params.get("pageNum").toString());
            Integer pageSize = Integer.parseInt(params.get("pageSize") == null ? "10" : params.get("pageSize").toString());
            String orderBy = params.get("orderBy") == null ? "" : params.get("orderBy").toString();
            Page<FileImage> page = new Page<>(pageNum, pageSize);
            QueryWrapper<FileImage> queryWrapper = new QueryWrapper<>();
            queryWrapper.orderBy(true, false, "create_time");
            IPage<FileImage> listImage = page(page, queryWrapper);
            result = ResultUtils.getResult(StatusCodeEnum._200, "请求成功", listImage);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private File getFileDir(String toPath, String extensionName) {
        String file_path = toPath + DateUtils.getCurrentDate();
        String fileName = DateUtils.formatDate(new Date(), DateUtils.TimeFormat.LONG_DATE_PATTERN_WITH_MILSEC_NO_SPLIT) + extensionName;
        File fileDir = new File(file_path);

        if (!fileDir.exists()) {
            // 递归生成文件夹
            fileDir.mkdirs();
        }

        File file = new File(fileDir, fileName);

        try {
            if (!file.isFile())
                file.createNewFile();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return file;
    }
}
