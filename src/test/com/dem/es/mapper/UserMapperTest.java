package com.dem.es.mapper;

import com.dem.es.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


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