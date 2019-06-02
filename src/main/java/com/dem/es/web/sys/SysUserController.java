package com.dem.es.web.sys;

import com.dem.es.entity.dto.LoginUserDTO;
import com.dem.es.util.Result;
import com.dem.es.util.ShiroUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description: 描述当前类
 * @Author Mr.p Email:huangdemo@shein.com
 * @Date create in 2019/6/2 0:41
 */
@RequestMapping("sys/user")
@RestController
@Api(tags = "系统用户")
public class SysUserController{


    @GetMapping("info")
    @ApiOperation(notes = "获取当前用户信息", value = "获取当前用户信息", response = LoginUserDTO.class)
    public Result getInfo(){
        LoginUserDTO user = (LoginUserDTO)ShiroUtils.getSubject().getPrincipal();
        return Result.success(user);
    }
}
