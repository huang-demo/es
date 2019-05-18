package com.dem.es.service;

import com.dem.es.entity.po.SysRole;

import java.util.List;

public interface SysRoleService {
    /**
     *
     * @param userId
     * @return
     */
    List<SysRole> selectByUser(Long userId);
}
