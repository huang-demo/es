package com.dem.es.mapper;

import com.dem.es.entity.po.SysMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysMenuMapper {
    int deleteByPrimaryKey(Long menuId);

    int insert(SysMenu record);

    int insertSelective(SysMenu record);

    SysMenu selectByPrimaryKey(Long menuId);

    int updateByPrimaryKeySelective(SysMenu record);

    int updateByPrimaryKey(SysMenu record);

    /**
     *
     * @return
     */
    List<SysMenu> selectMenuByType(@Param("pid") Long pid, @Param("type") Integer type);
}