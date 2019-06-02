package com.dem.es.entity.vo;

import lombok.Data;

import java.util.List;

@Data
public class MenuVO {

    private Long menuId;
    private Long parentId;
    private String parentName;
    private String name;
    private String url;
    private String perms;
    private Integer type;
    private String icon;
    private Integer orderNum;
    private Integer open;
    private List<MenuVO> list;
}
