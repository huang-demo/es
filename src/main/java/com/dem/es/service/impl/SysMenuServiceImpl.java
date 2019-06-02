package com.dem.es.service.impl;

import com.dem.es.entity.po.SysMenu;
import com.dem.es.entity.vo.MenuVO;
import com.dem.es.mapper.SysMenuMapper;
import com.dem.es.service.SysMenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SysMenuServiceImpl implements SysMenuService {

    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Override
    public List<MenuVO> getAllMenu() {

        List<SysMenu> list = sysMenuMapper.selectMenuByType(0L, SysMenu.MenuType.DIR.getCode());

        return list.stream().map(menu -> {
            MenuVO vo = converVO(menu);
            setChildNode(vo);
            return vo;
        }).collect(Collectors.toList());
    }

    private MenuVO converVO(SysMenu menu) {
        MenuVO vo = new MenuVO();
        vo.setMenuId(menu.getMenuId());
        vo.setParentId(menu.getParentId());
        vo.setName(menu.getName());
        vo.setIcon(menu.getIcon());
        vo.setType(menu.getType());
        vo.setUrl(menu.getUrl());
        return vo;
    }

    private void setChildNode(MenuVO vo) {
        if (vo == null) {
            return;
        }
        List<SysMenu> childList = sysMenuMapper.selectMenuByType(vo.getMenuId(), SysMenu.MenuType.MENU.getCode());

        List<MenuVO> list = childList.stream().map(menu -> {
            MenuVO cur = converVO(menu);
            setChildNode(cur);
            return cur;
        }).collect(Collectors.toList());
        vo.setList(list);
    }

    @Override
    public List<MenuVO> selectMenuByParentId(Long parentId) {
        return null;
    }
}
