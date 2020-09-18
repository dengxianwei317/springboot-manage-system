package com.spring.controller.file;

import com.spring.entity.common.Result;
import com.spring.service.file.FileImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * <p>
 * 图片
 * </p>
 *
 * @author dxw
 * @since 2020-08-29
 */
@Controller
@RequestMapping("api/file")
public class ImageController {
    @Autowired
    FileImageService imageService;

    @PostMapping(value = "/upload")
    @ResponseBody
    public Result upload(@RequestParam(name = "file") MultipartFile[] files) {
        Result result = null;
        try {
            result = imageService.upload(files);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @GetMapping(value = "/getPageList")
    @ResponseBody
    public Result getPageList(@RequestParam Map<Object, Object> params) {
        Result result = null;
        try {
            result = imageService.getPageListFile(params);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
