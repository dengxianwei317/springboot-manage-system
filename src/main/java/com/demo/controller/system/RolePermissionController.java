package com.demo.controller.system;

import com.demo.enums.StatusCodeEnum;
import com.demo.entity.common.Result;
import com.demo.service.system.SysRolePermissionService;
import com.demo.utils.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("api/system/rolePermission")
public class RolePermissionController {


    @Autowired
    SysRolePermissionService rolePermissionService;

    @PostMapping(value = "/add")
    @ResponseBody
    public Result add(@RequestParam(name = "PermissionIds") List<String> listPermissionId,
                      @RequestParam(name = "roleId") String roleId) {
        Result result = null;
        try {
            Boolean isOK = rolePermissionService.batchAdd(listPermissionId, roleId);

            if (isOK)
                result = ResultUtils.getResult(StatusCodeEnum._200, "保存成功", null);
            else
                result = ResultUtils.getResult(StatusCodeEnum._800, "保存失败", null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @DeleteMapping(value = "/delete")
    @ResponseBody
    public Result delete(@RequestParam(name = "roleId") String roleId,
                         @RequestParam(name = "ids") List<String> listPermissionId) {
        Result result = null;
        try {
            Boolean isOK = rolePermissionService.batchDelete(roleId, listPermissionId);

            if (isOK)
                result = ResultUtils.getResult(StatusCodeEnum._200, "删除成功", null);
            else
                result = ResultUtils.getResult(StatusCodeEnum._800, "删除失败", null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @GetMapping(value = "/getCheckedPermissionIds")
    @ResponseBody
    public Result getCheckedPermissionIds(String roleId) {
        Result result = null;
        List<String> listPermissionId = null;

        try {
            listPermissionId = rolePermissionService.getCheckedPermissionIds(roleId);
            result = ResultUtils.getResult(StatusCodeEnum._200, StatusCodeEnum._200.getMsg(), listPermissionId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
