package com.dem.es.entity.po;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="userinfo")
@Data
public class UserInfo implements Serializable{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String password;
    private String address;
    private String salt;
    private Date createTime;
    private Long createUser;

}
