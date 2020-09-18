package com.demo.controller.system;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.demo.constant.SysUserConstant;
import com.demo.enums.StatusCodeEnum;
import com.demo.entity.common.Result;
import com.demo.entity.system.SystemUser;
import com.demo.service.system.SysUserService;
import com.demo.utils.ResultUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户
 * </p>
 *
 * @author dxw
 * @since 2020-08-29
 */

@Controller
@RequestMapping("api/system/user")
public class UserController {
    @Autowired
    SysUserService userService;

    @RequiresPermissions(SysUserConstant.SYS_USER_ADD)
    @PostMapping(value = "/add")
    @ResponseBody
    public Result add(SystemUser user) {
        Result result = null;
        try {
            Boolean isOK = userService.add(user);

            if (isOK)
                result = ResultUtils.getResult(StatusCodeEnum._200, "保存成功", null);
            else
                result = ResultUtils.getResult(StatusCodeEnum._800, "保存失败", null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @RequiresPermissions(SysUserConstant.SYS_USER_QUERY)
    @GetMapping(value = "/select")
    @ResponseBody
    public Result select(String id) {
        Result result = null;
        try {
            SystemUser user = userService.getById(id);
            result = ResultUtils.getResult(StatusCodeEnum._200, StatusCodeEnum._200.getMsg(), user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @RequiresPermissions(SysUserConstant.SYS_USER_UPDATE)
    @PostMapping(value = "/update")
    @ResponseBody
    public Result update(SystemUser user) {
        Result result = null;
        try {
            Boolean isOK = userService.updateById(user);

            if (isOK)
                result = ResultUtils.getResult(StatusCodeEnum._200, "保存成功", null);
            else
                result = ResultUtils.getResult(StatusCodeEnum._800, "保存失败", null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @RequiresPermissions(SysUserConstant.SYS_USER_DELETE)
    @DeleteMapping(value = "/delete")
    @ResponseBody
    public Result delete(@RequestParam(name = "ids") List<String> listIds) {
        Result result = null;
        try {
            Boolean isOK = userService.removeByIds(listIds);

            if (isOK)
                result = ResultUtils.getResult(StatusCodeEnum._200, "删除成功", null);
            else
                result = ResultUtils.getResult(StatusCodeEnum._800, "删除失败", null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @RequiresPermissions(SysUserConstant.SYS_USER_QUERY)
    @GetMapping(value = "/getPageList")
    @ResponseBody
    public Result getPageList(SystemUser user, @RequestParam(name = "pageIndex") Integer pageIndex,
                              @RequestParam(name = "pageSize") Integer pageSize) {
        Result result = null;

        try {
            IPage<SystemUser> listMenu = userService.getPageList(user, pageIndex, pageSize);
            result = ResultUtils.getResult(StatusCodeEnum._200, StatusCodeEnum._200.getMsg(), listMenu);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @RequiresPermissions(SysUserConstant.SYS_USER_QUERY)
    @GetMapping(value = "/getCheckedPageList")
    @ResponseBody
    public Result getCheckedPageList(@RequestParam Map<String, Object> map,
                                     @RequestParam(name = "pageIndex") Integer pageIndex,
                                     @RequestParam(name = "pageSize") Integer pageSize) {
        Result result = null;

        try {
            IPage<SystemUser> listUser = userService.getCheckedPageList(map, pageIndex, pageSize);
            result = ResultUtils.getResult(StatusCodeEnum._200, StatusCodeEnum._200.getMsg(), listUser);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @RequiresPermissions(SysUserConstant.SYS_USER_UPDATE)
    @PostMapping(value = "/updateAccount")
    @ResponseBody
    public Result updateAccount(SystemUser user) {
        Result result = null;
        try {
            Boolean isOK = userService.updateAccount(user);

            if (isOK)
                result = ResultUtils.getResult(StatusCodeEnum._200, "修改成功", null);
            else
                result = ResultUtils.getResult(StatusCodeEnum._800, "修改失败", null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @RequiresPermissions(SysUserConstant.SYS_USER_QUERY)
    @GetMapping(value = "/checkAccount")
    @ResponseBody
    public Result checkAccount(String account) {
        Result result;
        try {
            Boolean isOK = userService.checkAccount(account);
            result = ResultUtils.getResult(StatusCodeEnum._200, StatusCodeEnum._200.getMsg(), isOK);
        } catch (Exception e) {
            result = ResultUtils.getResult(StatusCodeEnum._500, StatusCodeEnum._500.getMsg(), null);
            e.printStackTrace();
        }
        return result;
    }
}
