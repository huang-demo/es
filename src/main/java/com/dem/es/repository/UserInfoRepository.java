package com.dem.es.repository;

import com.dem.es.entity.po.UserInfo;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface UserInfoRepository extends Repository<UserInfo, Long> {

    List<UserInfo> findByName(String name);


}
