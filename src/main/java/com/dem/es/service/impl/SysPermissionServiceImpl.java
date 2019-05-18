package com.dem.es.service.impl;

import com.dem.es.entity.po.SysPermission;
import com.dem.es.mapper.SysPermissionMapper;
import com.dem.es.service.SysPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Set;

@Service
public class SysPermissionServiceImpl implements SysPermissionService {
    @Autowired
    private SysPermissionMapper sysPermissionMapper;
    @Override
    public List<SysPermission> selectByRoles(Set<String> roleSet) {
        if(roleSet == null||roleSet.size()==0){
            return Collections.emptyList();
        }
        return sysPermissionMapper.selectByRoles(roleSet);
    }
}
