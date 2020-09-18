package com.demo.dao.system;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.demo.entity.system.SystemUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  用户
 * </p>
 *
 * @author dxw
 * @since 2020-08-29
 */

@Repository
public interface SysUserDao extends BaseMapper<SystemUser> {
    public List<SystemUser> getCheckedList(IPage<SystemUser> page, @Param("query") Map<String, Object> query);

    public SystemUser getEntity(String userId);
}
