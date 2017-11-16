package com.dem.es.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter@Getter
public class User {
    private Long id;
    private String userName;
    private String passWord;
    private String email;
    private Date birthDay;
    private int sex;
    private Date createDate;
}
