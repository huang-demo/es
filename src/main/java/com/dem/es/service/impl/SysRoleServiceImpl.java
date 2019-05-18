package com.dem.es.service.impl;

import com.dem.es.entity.po.SysRole;
import com.dem.es.mapper.SysRoleMapper;
import com.dem.es.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysRoleServiceImpl implements SysRoleService {
    @Autowired
    private SysRoleMapper sysRoleMapper;
    @Override
    public List<SysRole> selectByUser(Long userId) {
        return sysRoleMapper.findByUserId(userId);
    }
}
