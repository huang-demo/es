package com.dem.es.entity.po;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
@Data
@Entity
public class SysUserRole {
    @Id
    @GeneratedValue
    private Long id;

    private Long uid;

    private Long rid;

}