package com.dem.es.web;

import com.dem.es.domain.UserInfo;
import com.dem.es.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/userInfo")
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;

    @PostMapping("/list")
    public List<UserInfo> getAll() {
        List<UserInfo> list = userInfoService.findAll();
        for (UserInfo userInfo : list) {
            System.out.println(userInfo.getId() + userInfo.getName());
        }
        return list;
    }

    @GetMapping("/{id}")
    public UserInfo getById(@PathVariable Long id) {
        return userInfoService.findById(id);
    }
}
