package com.dem.es.repository;

import com.dem.es.domain.UserInfo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface UserInfoRepository extends Repository<UserInfo, Long> {

    List<UserInfo> findByName(String name);


    @Query(value = "select * from #{#entityName} u where u.name=?1", nativeQuery = true)
    List<UserInfo> findByName2(String name);

}
