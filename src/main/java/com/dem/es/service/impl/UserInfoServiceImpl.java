package com.dem.es.service.impl;

import com.dem.es.entity.po.UserInfo;
import com.dem.es.repository.UserInfoJpaRepository;
import com.dem.es.repository.UserInfoRepository;
import com.dem.es.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private UserInfoRepository userInfoRepository;
    @Autowired
    private UserInfoJpaRepository userInfoJpaRepository;

    @Override
    public UserInfo findById(Long id) {
        return userInfoJpaRepository.getOne(id);
    }

    @Override
    public List<UserInfo> findAll() {
        return userInfoJpaRepository.findAll();
    }

    @Override
    public List<UserInfo> findByName(String name) {
        return userInfoRepository.findByName(name);
    }


}
