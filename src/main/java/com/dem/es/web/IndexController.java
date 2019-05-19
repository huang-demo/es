package com.dem.es.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController extends BaseController{
    //首页
    @GetMapping(value="index")
    public String index() {
        return "index";
    }
}
