package com.dem.es.service;

import com.dem.es.entity.po.SysUser;

public interface SysUserService {

    SysUser getByUserName(String name);
}
