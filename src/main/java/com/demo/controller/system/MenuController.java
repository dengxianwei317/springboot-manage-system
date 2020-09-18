package com.demo.controller.system;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.demo.constant.SysMenuConstant;
import com.demo.enums.StatusCodeEnum;
import com.demo.entity.common.Result;
import com.demo.entity.system.SystemMenu;
import com.demo.service.system.SysMenuService;
import com.demo.utils.ResultUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * <p>
 * 菜单
 * </p>
 *
 * @author dxw
 * @since 2020-08-29
 */
@Controller
@RequestMapping("api/system/menu")
public class MenuController {

    @Autowired
    SysMenuService menuService;

    @RequiresPermissions(SysMenuConstant.SYS_MENU_ADD)
    @PostMapping(value = "/add")
    @ResponseBody
    public Result add(SystemMenu menu) {
        Result result = null;
        try {
            Boolean isOK = menuService.add(menu);

            if (isOK)
                result = ResultUtils.getResult(StatusCodeEnum._200, "保存成功", null);
            else
                result = ResultUtils.getResult(StatusCodeEnum._800, "保存失败", null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @RequiresPermissions(SysMenuConstant.SYS_MENU_QUERY)
    @GetMapping(value = "/select")
    @ResponseBody
    public Result select(String id) {
        Result result = null;
        try {
            SystemMenu menu = menuService.getById(id);
            result = ResultUtils.getResult(StatusCodeEnum._200, StatusCodeEnum._200.getMsg(), menu);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @RequiresPermissions(SysMenuConstant.SYS_MENU_UPDATE)
    @PostMapping(value = "/update")
    @ResponseBody
    public Result update(SystemMenu menu) {
        Result result = null;
        Boolean isOK = false;
        try {
            isOK = menuService.updateById(menu);
            if (isOK)
                result = ResultUtils.getResult(StatusCodeEnum._200, "保存成功", null);
            else
                result = ResultUtils.getResult(StatusCodeEnum._800, "保存失败", null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @RequiresPermissions(SysMenuConstant.SYS_MENU_DELETE)
    @DeleteMapping(value = "/delete")
    @ResponseBody
    public Result delete(@RequestParam(name = "ids") List<String> listIds) {
        Result result = null;
        Boolean isOK = false;
        try {
            isOK = menuService.removeByIds(listIds);
            if (isOK)
                result = ResultUtils.getResult(StatusCodeEnum._200, "删除成功", null);
            else
                result = ResultUtils.getResult(StatusCodeEnum._800, "删除失败", null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @RequiresPermissions(SysMenuConstant.SYS_MENU_QUERY)
    @GetMapping(value = "/getPageList")
    @ResponseBody
    public Result getPageList(SystemMenu menu, @RequestParam(name = "pageIndex") Integer pageIndex,
                              @RequestParam(name = "pageSize") Integer pageSize) {
        Result result = null;

        try {
            IPage<SystemMenu> listMenu = menuService.getPageList(menu, pageIndex, pageSize);
            result = ResultUtils.getResult(StatusCodeEnum._200, StatusCodeEnum._200.getMsg(), listMenu);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /*
    * 获取角色分配的菜单
    * */
    @RequiresPermissions(SysMenuConstant.SYS_MENU_QUERY)
    @GetMapping(value = "/getListPermissionMenu")
    @ResponseBody
    public Result getListPermissionMenu(@RequestParam(name = "roleIds") List<String> listRoleId) {
        Result result = null;

        try {
            List<SystemMenu> listMenu = menuService.getListPermissionMenu(listRoleId);
            result = ResultUtils.getResult(StatusCodeEnum._200, StatusCodeEnum._200.getMsg(), listMenu);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /*
    * 获取所有菜单
    * */
    @RequiresPermissions(SysMenuConstant.SYS_MENU_QUERY)
    @GetMapping(value = "/getListTreeMenu")
    @ResponseBody
    public Result getListTreeMenu() {
        Result result = null;
        try {
            List<SystemMenu> listMenu = menuService.getListTreeMenu();
            result = ResultUtils.getResult(StatusCodeEnum._200, StatusCodeEnum._200.getMsg(), listMenu);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @RequiresPermissions(SysMenuConstant.SYS_MENU_QUERY)
    @GetMapping(value = "/checkRoutePath")
    @ResponseBody
    public Result checkRoutePath(String routePath) {
        Result result;
        try {
            Boolean isOK = menuService.checkRoutePath(routePath);
            result = ResultUtils.getResult(StatusCodeEnum._200, StatusCodeEnum._200.getMsg(), isOK);
        } catch (Exception e) {
            result = ResultUtils.getResult(StatusCodeEnum._500, StatusCodeEnum._500.getMsg(), null);
            e.printStackTrace();
        }
        return result;
    }
}
