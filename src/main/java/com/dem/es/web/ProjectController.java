package com.dem.es.web;

import com.dem.es.domain.ProjectInfo;
import com.dem.es.service.ProjectInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/project")
public class ProjectController {
    @Autowired
    private ProjectInfoService projectInfoService;

    @GetMapping("/all")
    public List<ProjectInfo> getAll() {
        return projectInfoService.getAll();
    }

    @GetMapping("/autoInseart")
    public String autoInseart(){
        projectInfoService.inseart2Elastic();
        return  "Hello";
    }
    @GetMapping("/query/{page}/{pageSize}")
    public Object query(String kw,@PathVariable Integer page,@PathVariable Integer pageSize){
        return projectInfoService.query(kw,page,pageSize);
    }
}
