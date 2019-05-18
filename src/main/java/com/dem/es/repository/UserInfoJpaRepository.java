package com.dem.es.repository;

import com.dem.es.entity.po.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserInfoJpaRepository extends JpaRepository<UserInfo, Long> {
}
