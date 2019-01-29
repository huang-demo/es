package com.dem.es.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("page")
public class PageController {

    @GetMapping("/person")
    public String personList() {
        return "/person/personList";
    }

    @GetMapping("/{page}")
    public String page(@PathVariable String page) {
        return page;
    }
}
