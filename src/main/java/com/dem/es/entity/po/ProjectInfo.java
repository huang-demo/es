package com.dem.es.entity.po;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "projectinfo")
@Setter
@Getter
public class ProjectInfo implements Serializable {
    @Id
    @GeneratedValue
    private Long id;
    private Long pid;
    private String name;
    private Double amount;
    private String content;
    @Column(name="createtime")
    private Date createTime;
    @Column(name="updatetime")
    private Date updateTime;
    @Column(name="projecttype")
    private Integer projectType;
}
