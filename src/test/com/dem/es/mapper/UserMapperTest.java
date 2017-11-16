package com.dem.es.mapper;

import com.dem.es.controller.Application;
import com.dem.es.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;


@RunWith(SpringJUnit4ClassRunner.class)
@Configuration()
public class UserMapperTest {
    @Autowired
    private UserMapper userMapper;

    @Test
    @Rollback
    public void findById() throws Exception {
        User user = userMapper.findById(25L);
        System.out.println(user.getId());

    }


}