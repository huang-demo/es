package com.dem.es.web;

import com.dem.es.domain.ProjectInfo;
import com.dem.es.service.ProjectInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
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
    public String autoInseart() {
        projectInfoService.batchAdd();
        return "Hello";
    }

    @GetMapping("/query/{page}/{pageSize}")
    public Object query(String kw, @PathVariable Integer page, @PathVariable Integer pageSize) {
        return projectInfoService.query(kw, page, pageSize);
    }

    @PostMapping("/projectInfo/del")
    public String delByProjectName(String projectName) {
        projectInfoService.deleteByProjectName(projectName);
        return "hh";
    }

    @GetMapping("/add/{id}")
    public String addOne(@PathVariable Long id) {
        try {
            return projectInfoService.addOne(id);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    @PostMapping("/getSouceName")
   public Object getSourceName(String kw){
        return projectInfoService.searchProjectName(kw);
   }
}
