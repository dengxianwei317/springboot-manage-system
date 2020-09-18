package com.demo.dao.system;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.demo.entity.system.SystemPermission;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface SysPermissionDao extends BaseMapper<SystemPermission> {
    public List<SystemPermission> getCheckedList(IPage<SystemPermission> page, @Param("query") Map<String, Object> query);

    public List<String> getListCode(String roleId);
}
