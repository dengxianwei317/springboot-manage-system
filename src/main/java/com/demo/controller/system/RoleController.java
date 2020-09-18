package com.demo.controller.system;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.demo.constant.SysRoleConstant;
import com.demo.enums.StatusCodeEnum;
import com.demo.entity.common.Result;
import com.demo.entity.system.SystemRole;
import com.demo.service.system.SysRoleService;
import com.demo.utils.ResultUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * <p>
 * 角色
 * </p>
 *
 * @author dxw
 * @since 2020-08-29
 */
@Controller
@RequestMapping("api/system/role")
public class RoleController {
    @Autowired
    SysRoleService roleService;

    @RequiresPermissions(SysRoleConstant.SYS_ROLE_ADD)
    @PostMapping(value = "/add")
    @ResponseBody
    public Result add(SystemRole role) {
        Result result = null;
        try {
            Boolean isOK = roleService.add(role);

            if (isOK)
                result = ResultUtils.getResult(StatusCodeEnum._200, "保存成功", null);
            else
                result = ResultUtils.getResult(StatusCodeEnum._800, "保存失败", null);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @RequiresPermissions(SysRoleConstant.SYS_ROLE_QUERY)
    @GetMapping(value = "/select")
    @ResponseBody
    public Result select(String id) {
        Result result = null;
        try {
            SystemRole role = roleService.getById(id);
            result = ResultUtils.getResult(StatusCodeEnum._200, StatusCodeEnum._200.getMsg(), role);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @RequiresPermissions(SysRoleConstant.SYS_ROLE_UPDATE)
    @PostMapping(value = "/update")
    @ResponseBody
    public Result update(SystemRole role) {
        Result result = null;
        try {
            Boolean isOK = roleService.updateById(role);

            if (isOK)
                result = ResultUtils.getResult(StatusCodeEnum._200, "保存成功", null);
            else
                result = ResultUtils.getResult(StatusCodeEnum._800, "保存失败", null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @RequiresPermissions(SysRoleConstant.SYS_ROLE_DELETE)
    @DeleteMapping(value = "/delete")
    @ResponseBody
    public Result delete(@RequestParam(name = "ids") List<String> listIds) {
        Result result = null;
        try {
            Boolean isOK = roleService.removeByIds(listIds);

            if (isOK)
                result = ResultUtils.getResult(StatusCodeEnum._200, "删除成功", null);
            else
                result = ResultUtils.getResult(StatusCodeEnum._800, "删除失败", null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @RequiresPermissions(SysRoleConstant.SYS_ROLE_QUERY)
    @GetMapping(value = "/getPageList")
    @ResponseBody
    public Result getPageList(SystemRole role, @RequestParam(name = "pageIndex") Integer pageIndex,
                              @RequestParam(name = "pageSize") Integer pageSize) {
        Result result = null;

        try {
            IPage<SystemRole> listMenu = roleService.getPageList(role, pageIndex, pageSize);
            result = ResultUtils.getResult(StatusCodeEnum._200, StatusCodeEnum._200.getMsg(), listMenu);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @RequiresPermissions(SysRoleConstant.SYS_ROLE_QUERY)
    @GetMapping(value = "/checkRoleName")
    @ResponseBody
    public Result checkRoleName(String name) {
        Result result = null;
        try {
            Boolean isOK = roleService.checkRoleName(name);
            result = ResultUtils.getResult(StatusCodeEnum._200, StatusCodeEnum._200.getMsg(), isOK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
