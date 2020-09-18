package com.spring.controller.system;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.spring.constant.SysPermissionConstant;
import com.spring.enums.StatusCodeEnum;
import com.spring.entity.common.Result;
import com.spring.entity.system.SystemPermission;
import com.spring.service.system.SysPermissionService;
import com.spring.utils.ResultUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("api/system/permission")
public class PermissionController {

    @Autowired
    SysPermissionService permissionService;

    @RequiresPermissions(SysPermissionConstant.SYS_PERMISSION_ADD)
    @PostMapping(value = "/add")
    @ResponseBody
    public Result add(SystemPermission entity) {
        Result result = null;
        try {
            Boolean isOK = permissionService.add(entity);

            if (isOK)
                result = ResultUtils.getResult(StatusCodeEnum._200, "保存成功", null);
            else
                result = ResultUtils.getResult(StatusCodeEnum._800, "保存失败", null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @RequiresPermissions(SysPermissionConstant.SYS_PERMISSION_QUERY)
    @GetMapping(value = "/select")
    @ResponseBody
    public Result select(String id) {
        Result result = null;
        try {
            SystemPermission entity = permissionService.getById(id);
            result = ResultUtils.getResult(StatusCodeEnum._200, StatusCodeEnum._200.getMsg(), entity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @RequiresPermissions(SysPermissionConstant.SYS_PERMISSION_UPDATE)
    @PostMapping(value = "/update")
    @ResponseBody
    public Result update(SystemPermission entity) {
        Result result = null;
        Boolean isOK = false;
        try {
            isOK = permissionService.updateById(entity);
            if (isOK)
                result = ResultUtils.getResult(StatusCodeEnum._200, "保存成功", null);
            else
                result = ResultUtils.getResult(StatusCodeEnum._800, "保存失败", null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @RequiresPermissions(SysPermissionConstant.SYS_PERMISSION_DELETE)
    @DeleteMapping(value = "/delete")
    @ResponseBody
    public Result delete(@RequestParam(name = "ids") List<String> listIds) {
        Result result = null;
        Boolean isOK = false;
        try {
            isOK = permissionService.removeByIds(listIds);
            if (isOK)
                result = ResultUtils.getResult(StatusCodeEnum._200, "删除成功", null);
            else
                result = ResultUtils.getResult(StatusCodeEnum._800, "删除失败", null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @RequiresPermissions(SysPermissionConstant.SYS_PERMISSION_QUERY)
    @GetMapping(value = "/getPageList")
    @ResponseBody
    public Result getPageList(SystemPermission entity, @RequestParam(name = "pageIndex") Integer pageIndex,
                              @RequestParam(name = "pageSize") Integer pageSize) {
        Result result = null;

        try {
            IPage<SystemPermission> listMenu = permissionService.getPageList(entity, pageIndex, pageSize);
            result = ResultUtils.getResult(StatusCodeEnum._200, StatusCodeEnum._200.getMsg(), listMenu);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @RequiresPermissions(SysPermissionConstant.SYS_PERMISSION_QUERY)
    @GetMapping(value = "/getCheckedPageList")
    @ResponseBody
    public Result getCheckedPageList(@RequestParam Map<String, Object> map,
                                 @RequestParam(name = "pageIndex") Integer pageIndex,
                                 @RequestParam(name = "pageSize") Integer pageSize) {
        Result result = null;

        try {
            IPage<SystemPermission> listData = permissionService.getCheckedPageList(map, pageIndex, pageSize);
            result = ResultUtils.getResult(StatusCodeEnum._200, StatusCodeEnum._200.getMsg(), listData);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @RequiresPermissions(SysPermissionConstant.SYS_PERMISSION_QUERY)
    @GetMapping(value = "/checkPermission")
    @ResponseBody
    public Result checkPermission(SystemPermission entity) {
        Result result;
        try {
            Boolean isOK = permissionService.checkPermission(entity);
            result = ResultUtils.getResult(StatusCodeEnum._200, StatusCodeEnum._200.getMsg(), isOK);
        } catch (Exception e) {
            result = ResultUtils.getResult(StatusCodeEnum._500, StatusCodeEnum._500.getMsg(), null);
            e.printStackTrace();
        }
        return result;
    }
}
