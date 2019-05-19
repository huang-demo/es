package com.dem.es.entity.po;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
@Data
@Entity
public class SysPermission {
    @Id
    @GeneratedValue
    private String id;

    private String url;

    private String name;


}