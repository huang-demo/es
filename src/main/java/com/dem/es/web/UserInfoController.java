package com.dem.es.web;

import com.dem.es.entity.po.UserInfo;
import com.dem.es.service.UserInfoService;
import com.dem.es.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/userInfo")
@Api(value = "用户模块")
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;

    @ApiOperation(value = "/按名称查询", notes = "")
    @PostMapping("/list")
    public Result<List<UserInfo>> list(String kw) {
        List<UserInfo> list = userInfoService.findByName(kw);
        for (UserInfo userInfo : list) {
            System.out.println(userInfo.getId() + userInfo.getName());
        }
        return Result.success(list);
    }

    @ApiOperation(value = "/按用户id查询", notes = "")
    @GetMapping("/{id}")
    public Result<UserInfo> getById(@PathVariable Long id) {
        return Result.success(userInfoService.findById(id));
    }
}
