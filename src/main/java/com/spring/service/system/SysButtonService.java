package com.spring.service.system;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.spring.entity.system.SystemButton;

import java.util.List;

/**
 * <p>
 * 按钮
 * </p>
 *
 * @author dxw
 * @since 2020-08-29
 */
public interface SysButtonService extends IService<SystemButton>{
    public Boolean add(SystemButton button);

    public Boolean checkButton(SystemButton button);

    public List<SystemButton> getCheckedList(List<String> listRoleId, String menuId);

    public List<SystemButton> getList();

    public IPage<SystemButton> getPageList(SystemButton button, Integer pageIndex, Integer pageSize);
}
