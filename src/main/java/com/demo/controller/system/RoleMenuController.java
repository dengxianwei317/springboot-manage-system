package com.demo.controller.system;


import com.demo.enums.StatusCodeEnum;
import com.demo.entity.common.Result;
import com.demo.entity.system.SystemRoleMenu;
import com.demo.service.system.SysRoleMenuService;
import com.demo.utils.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * <p>
 * 角色和菜单
 * </p>
 *
 * @author dxw
 * @since 2020-08-29
 */
@Controller
@RequestMapping("api/system/roleMenu")
public class RoleMenuController {

    @Autowired
    SysRoleMenuService roleMenuService;

    @PostMapping(value = "/addAndDelete")
    @ResponseBody
    public Result addAndDelete(@RequestParam(name = "addMenuIds", required = false) List<String> listAddMenuId,
                               @RequestParam(name = "deleteMenuIds", required = false) List<String> listDeleteMenuId,
                               @RequestParam(name = "roleId") String roleId) {
        Boolean isOK = true;
        Result result = null;
        try {
            if (listAddMenuId != null && listAddMenuId.size() > 0)
                isOK = roleMenuService.batchAdd(listAddMenuId, roleId);

             if (listDeleteMenuId != null && listDeleteMenuId.size() > 0)
                isOK = roleMenuService.batchDelete(listDeleteMenuId, roleId);

            if (isOK)
                result = ResultUtils.getResult(StatusCodeEnum._200, "保存成功", isOK);
            else
                result = ResultUtils.getResult(StatusCodeEnum._800, "保存失败", isOK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @GetMapping(value = "/getCheckedList")
    @ResponseBody
    public Result getCheckedList(String roleId) {
        Result result = null;
        List<SystemRoleMenu> listRoleMenu = null;

        try {
            listRoleMenu = roleMenuService.getList(roleId);
            result = ResultUtils.getResult(StatusCodeEnum._200, StatusCodeEnum._200.getMsg(), listRoleMenu);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}
