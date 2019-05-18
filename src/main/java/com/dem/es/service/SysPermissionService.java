package com.dem.es.service;

import com.dem.es.entity.po.SysPermission;

import java.util.List;
import java.util.Set;

public interface SysPermissionService {
    /**
     *
     * @param roleSet
     * @return
     */
    List<SysPermission> selectByRoles(Set<String> roleSet);
}
