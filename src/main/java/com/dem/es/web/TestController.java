package com.dem.es.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/test")
@Api(value = "测试使用")
public class TestController {
    @ApiOperation(value = "打印json sayHello ", notes = "")
    @RequestMapping("/sayHello")
    @ResponseBody
    public String sayHello() {
        return "hello3211";
    }

    @ApiOperation(value = "跳转首页 jsp", notes = "目前配置没有成功")
    @GetMapping("/index")
    public String index(ModelMap model, HttpServletRequest request) {
        model.addAttribute("name", "Hello world");
        return "index";
    }

}
