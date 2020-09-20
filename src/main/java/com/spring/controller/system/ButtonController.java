package com.spring.controller.system;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.spring.constant.SysButtonConstant;
import com.spring.enums.StatusCodeEnum;
import com.spring.entity.common.Result;
import com.spring.entity.system.SystemButton;
import com.spring.service.system.SysButtonService;
import com.spring.utils.ResultUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * <p>
 * 按钮
 * </p>
 *
 * @author dxw
 * @since 2020-08-29
 */
@Controller
@RequestMapping("api/system/button")
public class ButtonController {

    @Autowired
    SysButtonService buttonService;

    @RequiresPermissions(SysButtonConstant.SYS_BUTTON_ADD)
    @PostMapping(value = "/add")
    @ResponseBody
    public Result add(SystemButton button) {
        Result result = null;
        try {
            Boolean isOK = buttonService.add(button);

            if (isOK)
                result = ResultUtils.getResult(StatusCodeEnum._200, "保存成功", null);
            else
                result = ResultUtils.getResult(StatusCodeEnum._800, "保存失败", null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @RequiresPermissions(SysButtonConstant.SYS_BUTTON_QUERY)
    @GetMapping(value = "/select")
    @ResponseBody
    public Result select(String id) {
        Result result = null;
        try {
            SystemButton button = buttonService.getById(id);
            result = ResultUtils.getResult(StatusCodeEnum._200, StatusCodeEnum._200.getMsg(), button);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @RequiresPermissions(SysButtonConstant.SYS_BUTTON_UPDATE)
    @PostMapping(value = "/update")
    @ResponseBody
    public Result update(SystemButton button) {
        Result result = null;
        Boolean isOK = false;
        try {
            isOK = buttonService.updateById(button);
            if (isOK)
                result = ResultUtils.getResult(StatusCodeEnum._200, "保存成功", null);
            else
                result = ResultUtils.getResult(StatusCodeEnum._800, "保存失败", null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @RequiresPermissions(SysButtonConstant.SYS_BUTTON_DELETE)
    @DeleteMapping(value = "/delete")
    @ResponseBody
    public Result delete(@RequestParam(name = "ids") List<String> listIds) {
        Result result = null;
        Boolean isOK = false;
        try {
            isOK = buttonService.removeByIds(listIds);
            if (isOK)
                result = ResultUtils.getResult(StatusCodeEnum._200, "删除成功", null);
            else
                result = ResultUtils.getResult(StatusCodeEnum._800, "删除失败", null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @RequiresPermissions(SysButtonConstant.SYS_BUTTON_QUERY)
    @GetMapping(value = "/getPageList")
    @ResponseBody
    public Result getPageList(SystemButton button, @RequestParam(name = "pageIndex") Integer pageIndex,
                              @RequestParam(name = "pageSize") Integer pageSize) {
        Result result = null;

        try {
            IPage<SystemButton> listMenu = buttonService.getPageList(button, pageIndex, pageSize);
            result = ResultUtils.getResult(StatusCodeEnum._200, StatusCodeEnum._200.getMsg(), listMenu);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @RequiresPermissions(SysButtonConstant.SYS_BUTTON_QUERY)
    @GetMapping(value = "/getList")
    @ResponseBody
    public Result getList() {
        Result result = null;

        try {
            List<SystemButton> listButton = buttonService.getList();
            result = ResultUtils.getResult(StatusCodeEnum._200, StatusCodeEnum._200.getMsg(), listButton);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @GetMapping(value = "/getCheckedList")
    @ResponseBody
    public Result getCheckedList(@RequestParam(name = "roleIds") List<String> listRoleId,
                                 @RequestParam(name = "menuId") String menuId) {
        Result result = null;

        try {
            List<SystemButton> listButton = buttonService.getCheckedList(listRoleId, menuId);
            result = ResultUtils.getResult(StatusCodeEnum._200, StatusCodeEnum._200.getMsg(), listButton);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @RequiresPermissions(SysButtonConstant.SYS_BUTTON_QUERY)
    @GetMapping(value = "/checkButton")
    @ResponseBody
    public Result checkButton(SystemButton button) {
        Result result;
        try {
            Boolean isOK = buttonService.checkButton(button);
            result = ResultUtils.getResult(StatusCodeEnum._200, StatusCodeEnum._200.getMsg(), isOK);
        } catch (Exception e) {
            result = ResultUtils.getResult(StatusCodeEnum._500, StatusCodeEnum._500.getMsg(), null);
            e.printStackTrace();
        }
        return result;
    }
}
