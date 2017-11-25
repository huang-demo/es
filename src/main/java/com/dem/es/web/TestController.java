package com.dem.es.web;

import com.dem.es.service.JedisClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/test")
@Api(value = "测试使用")
public class TestController {

    @Autowired
    private JedisClient jedisClient;

    @ApiOperation(value = "打印json sayHello ", notes = "")
    @RequestMapping("/sayHello")
    @ResponseBody
    public String sayHello() {
        return "hello3211";
    }

    @ApiOperation(value = "跳转首页 jsp")
    @GetMapping("/index")
    public String index(ModelMap model, HttpServletRequest request) {
        model.addAttribute("name", "Hello world");
        return "index";
    }

    @ApiOperation(value = "redis新增key-value")
    @PostMapping("/redis")
    @ResponseBody
    public String redisSet(String key, String value) {
        jedisClient.set(key, value);
        return "SUCCESS";
    }

    @ApiOperation(value = "从redis中获取hash")
    @ApiImplicitParams({@ApiImplicitParam(name = "key", paramType = "String", value = "key值"), @ApiImplicitParam(name = "item", paramType = "String")})
    @GetMapping("/redis/hget")
    @ResponseBody
    public String hget(String key, String item) {
        return jedisClient.hget(key, item);
    }


}
