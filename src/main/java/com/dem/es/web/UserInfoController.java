package com.dem.es.web;

import com.dem.es.domain.UserInfo;
import com.dem.es.service.UserInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/userInfo")
@Api(value = "用户模块")
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;
    @ApiOperation(value = "/按名称查询",notes = "")
    @PostMapping("/list")
    public List<UserInfo> list(String kw) {
        List<UserInfo> list = userInfoService.findByName(kw);
        for (UserInfo userInfo : list) {
            System.out.println(userInfo.getId() + userInfo.getName());
        }
        return list;
    }

    @ApiOperation(value = "/按名称查询",notes = "")
    @GetMapping("/{id}")
    public UserInfo getById(@PathVariable Long id) {
        return userInfoService.findById(id);
    }
}
