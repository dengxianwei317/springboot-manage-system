package com.demo.controller.system;


import com.demo.enums.StatusCodeEnum;
import com.demo.entity.common.Result;
import com.demo.entity.system.SystemRoleMenuButton;
import com.demo.service.system.SysRoleMenuButtonService;
import com.demo.utils.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Map;


/**
 * <p>
 * 菜单和按钮
 * </p>
 *
 * @author dxw
 * @since 2020-08-29
 */
@Controller
@RequestMapping("api/system/roleMenuButton")
public class RoleMenuButtonController {

    @Autowired
    SysRoleMenuButtonService roleMenuButtonService;

    @PostMapping(value = "/batchAddAndDelete")
    @ResponseBody
    public Result batchAddAndDelete(@RequestBody Map<String, Object> mapData) {
        Result result = null;
        Boolean isOK = false;

        try {
            isOK = roleMenuButtonService.batchAddAndDelete(mapData);
            if (isOK)
                result = ResultUtils.getResult(StatusCodeEnum._200, "保存成功", isOK);
            else
                result = ResultUtils.getResult(StatusCodeEnum._800, "保存失败", isOK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /*
    * 获取页面已分配的按钮ID集合
    * */
    @GetMapping(value = "/getCheckedList")
    @ResponseBody
    public Result getCheckedList(String roleId, String menuId) {
        Result result = null;
        List<SystemRoleMenuButton> listRoleMenu = null;

        try {
            listRoleMenu = roleMenuButtonService.getCheckedList(roleId, menuId);
            result = ResultUtils.getResult(StatusCodeEnum._200, StatusCodeEnum._200.getMsg(), listRoleMenu);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}
