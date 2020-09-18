package com.demo.dao.system;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.demo.entity.system.SystemButton;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 按钮配置
 * </p>
 *
 * @author dxw
 * @since 2020-08-29
 */

@Repository
public interface SysButtonDao extends BaseMapper<SystemButton> {
    public List<SystemButton> getCheckedList(Map<String, Object> params);
}
