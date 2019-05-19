package com.dem.es.mapper;

import com.dem.es.entity.po.SysPermission;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

public interface SysPermissionMapper {
    int deleteByPrimaryKey(String id);

    int insert(SysPermission record);

    int insertSelective(SysPermission record);

    SysPermission selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SysPermission record);

    int updateByPrimaryKey(SysPermission record);

    /**
     *
     * @param roleSet
     * @return
     */
    List<SysPermission> selectByRoles(@Param("roleSet") Set<String> roleSet);
}