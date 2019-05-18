package com.dem.es.service.impl;

import com.dem.es.entity.po.SysUser;
import com.dem.es.repository.SysUserRepository;
import com.dem.es.service.SysUserService;
import com.dem.es.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysUserServiceImpl implements SysUserService {
    @Autowired
    private SysUserRepository sysUserRepository;
    @Override
    public SysUser getByUserName(String name) {
        if(StringUtil.isEmpty(name)){
            return null;
        }
        return sysUserRepository.findByUserName(name);
    }
}
