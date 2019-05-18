package com.dem.es.repository;

import com.dem.es.entity.po.SysUser;
import org.springframework.data.repository.Repository;

public interface SysUserRepository extends Repository<SysUser, Long> {

    SysUser findByUserName(String name);
}
