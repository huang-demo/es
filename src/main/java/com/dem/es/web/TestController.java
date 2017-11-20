package com.dem.es.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/test")
public class TestController {


    @RequestMapping("/sayHello")
    @ResponseBody
    public String sayHello() {
        return "hello3211";
    }

    @RequestMapping("/")
    public String index(ModelMap model){
        model.addAttribute("name","Hello world");
        return "index";
    }

}
