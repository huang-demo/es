package com.dem.es.entity.po;

import lombok.Data;
import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;
@Data
@Entity
public class SysUser {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String userName;

    private String email;

    private String password;

    private Date createTime;

    private Date lastLoginTime;

    private Integer status;

    private String salt;



    @Getter
    public enum UserState{
        DISABLE(0,"禁用"),
        ENABLE(1,"啓用"),
        ;
        private Integer code;
        private String name;

        UserState(Integer code, String name) {
            this.code = code;
            this.name = name;
        }
    }
}