package com.dem.es.entity.po;

import lombok.Data;
import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class SysMenu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long menuId;

    private Long parentId;

    private String name;

    private String url;

    private String perms;

    private Integer type;

    private String icon;

    private Integer orderNum;

    @Getter
    public static enum MenuType {
        DIR(0, "目录"),
        MENU(1, "菜单"),
        BUTTON(2, "按钮"),
        ;

        MenuType(Integer code, String name) {
            this.code = code;
            this.name = name;
        }

        private Integer code;
        private String name;
    }

}