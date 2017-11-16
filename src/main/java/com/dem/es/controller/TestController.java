package com.dem.es.controller;

import com.dem.es.entity.User;
import com.dem.es.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {
   // @Autowired
    private UserService userService;

    @RequestMapping("/sayHello")
    public String sayHello() {
        User user = userService.getById(25L);
        System.out.println(user);
        return "hello3211";
    }

    public String test() {
        return "test";
    }

}
