package com.dem.es.service;

import com.dem.es.entity.vo.MenuVO;

import java.util.List;

public interface SysMenuService {
    /**
     * 获取所有菜单
     *
     * @return
     */
    List<MenuVO> getAllMenu();

    /**
     * 或者子菜单
     *
     * @param parentId
     * @return
     */
    List<MenuVO> selectMenuByParentId(Long parentId);
}
