package com.dem.es.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "person")
//@JsonIgnoreProperties({ "handler","hibernateLazyInitializer" })//懒加载导致json数据对象传输异常
@Setter
@Getter
public class Person implements Serializable {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String address;
    private int age;
    @Column(name="createtime")
    private Date createTime;
    @Column(name="updatetime")
    private Date updateTime;


}
