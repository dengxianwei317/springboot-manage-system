package com.spring.service.system.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.spring.dao.system.SysButtonDao;
import com.spring.entity.system.SystemButton;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.spring.service.system.SysButtonService;
import com.spring.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * <p>
 * 按钮
 * </p>
 *
 * @author dxw
 * @since 2020-08-29
 */
@Service
public class SysButtonServiceImpl extends ServiceImpl<SysButtonDao, SystemButton> implements SysButtonService {

    @Autowired
    SysButtonDao buttonDao;

    @Override
    public Boolean add(SystemButton button) {
        Boolean isOK = false;

        try {
            if (checkButton(button))
                return isOK;

            if (StringUtils.isEmpty(button.getId()))
                button.setId(UUID.randomUUID().toString().replaceAll("-", ""));

            isOK = this.save(button);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isOK;
    }

    @Override
    public IPage<SystemButton> getPageList(SystemButton button, Integer pageIndex, Integer pageSize) {
        IPage<SystemButton> list = null;
        QueryWrapper<SystemButton> queryWrapper = null;
        try {
            queryWrapper = new QueryWrapper<>();

            queryWrapper.eq("is_delete", false).orderByDesc("sort");

            if (StringUtils.isNotEmpty(button.getIsEnabled()))
                queryWrapper.eq("is_enabled", button.getIsEnabled());

            if (StringUtils.isNotEmpty(button.getButtonType()))
                queryWrapper.eq("button_type", button.getButtonType());

            if (StringUtils.isNotEmpty(button.getButtonName()))
                queryWrapper.like("button_name", button.getButtonName());

            IPage<SystemButton> page = new Page<>(pageIndex, pageSize);
            list = this.page(page, queryWrapper);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<SystemButton> getList() {
        List<SystemButton> listButton = null;

        try {
            QueryWrapper<SystemButton> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("is_delete", false)
                    .eq("is_enabled", true).orderByAsc("sort");
            listButton = this.list(queryWrapper);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listButton;
    }

    /*
     * 获取页面分配的按钮
     * */
    @Override
    public List<SystemButton> getCheckedList(List<String> listRoleId, String menuId) {
        List<SystemButton> listButton = null;

        try {
            Map<String, Object> map = new HashMap<>();
            map.put("listRoleId", listRoleId);
            map.put("menuId", menuId);
            listButton = buttonDao.getCheckedList(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listButton;
    }

    @Override
    public Boolean checkButton(SystemButton button) {
        int count = 0;
        QueryWrapper<SystemButton> queryWrapper = null;
        try {
            queryWrapper = new QueryWrapper<>();

            if (StringUtils.isNotEmpty(button.getButtonName()))
                queryWrapper.eq("button_name", button.getButtonName());

            count = this.count(queryWrapper);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count > 0;
    }
}
