package com.dem.es.web;

import com.dem.es.service.JedisClient;
import com.dem.es.exception.MyException;
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
@Api(description = "测试使用")
public class TestController {



    @ApiOperation(value = "跳转首页 jsp")
    @GetMapping("/index")
    public String index(ModelMap model, HttpServletRequest request) {
        model.addAttribute("name", "Hello world");
        return "index";
    }
    @ApiOperation(value = "打印json sayHello ", notes = "")
    @GetMapping("/sayHello")
    @ResponseBody
    public String sayHello() {
        return "hello3211";
    }


    @ApiOperation(value = "测试全局错误页面")
    @PostMapping("/error")
    public String test2err() throws Exception {
        throw new Exception("hhyh");
    }

    @ApiOperation(value = "测试指定MyException拦截")
    @GetMapping("/jsonErr")
    @ResponseBody
    public String testJsonErr() throws MyException {
        throw new MyException("测试返回json出错!");
    }


}
