package com.dem.es.service.impl;

import com.dem.es.entity.User;
import com.dem.es.mapper.UserMapper;
import com.dem.es.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public User getById(Long id) {
        return userMapper.findById(id);
    }
}
