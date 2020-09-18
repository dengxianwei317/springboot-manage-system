package com.demo.controller.system;


import com.demo.enums.StatusCodeEnum;
import com.demo.entity.common.Result;
import com.demo.service.system.SysUserRoleService;
import com.demo.utils.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * <p>
 * 用户和角色
 * </p>
 *
 * @author dxw
 * @since 2020-08-29
 */
@Controller
@RequestMapping("api/system/userRole")
public class UserRoleController {

    @Autowired
    SysUserRoleService userRoleService;

    @PostMapping(value = "/add")
    @ResponseBody
    public Result add(@RequestParam(name = "userIds") List<String> listUserId,
                      @RequestParam(name = "roleId") String roleId) {
        Result result = null;
        try {
            Boolean isOK = userRoleService.batchAdd(listUserId, roleId);

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
    public Result delete(@RequestParam(name = "roleId") String roleId, @RequestParam(name = "ids") List<String> listUserId) {
        Result result = null;
        try {
            Boolean isOK = userRoleService.batchDelete(roleId, listUserId);

            if (isOK)
                result = ResultUtils.getResult(StatusCodeEnum._200, "删除成功", null);
            else
                result = ResultUtils.getResult(StatusCodeEnum._800, "删除失败", null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @GetMapping(value = "/getCheckedUserIds")
    @ResponseBody
    public Result getCheckedUserIds(String roleId) {
        Result result = null;
        List<String> listUserId = null;

        try {
            listUserId = userRoleService.getCheckedUserIds(roleId);
            result = ResultUtils.getResult(StatusCodeEnum._200, StatusCodeEnum._200.getMsg(), listUserId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
