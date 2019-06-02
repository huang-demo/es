package com.dem.es.entity.po;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "person")
@Data
public class Person implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String address;
    private int age;
    @Column(name="createtime")
    private Date createTime;
    @Column(name="updatetime")
    private Date updateTime;


}
