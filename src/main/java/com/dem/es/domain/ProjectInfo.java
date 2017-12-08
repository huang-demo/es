package com.dem.es.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Setter
@Getter
@Table(name = "projectInfo")
public class ProjectInfo implements Serializable {
    @Id
    @GeneratedValue
    private Long projectId;
    private Long pid;
    @Column(name = "projectName")
    private String projectName;
    private String content;
    @Column(name = "createDate")
    private Date createDate;
    private String user;
    private String phone;
    @Column(name = "projectType")
    private String projectType;
}
