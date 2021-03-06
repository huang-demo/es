package com.dem.es.web;

import com.dem.es.entity.req.ElasticReq;
import com.dem.es.service.ElasticPersonService;
import com.dem.es.util.PageBean;
import com.dem.es.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("elasticPerson")
@Api("es person 控制")
public class ElasticPersonController {

    @Autowired
    private ElasticPersonService elasticPersonService;


    @PostMapping("/searchBykw")
    @ApiOperation(tags = "按名称搜索",value = "按名称搜索",response = PageBean.class)
    public Result searchByKw(ElasticReq req) {
        try {
            PageBean pageBean = elasticPersonService.search(req);
            return Result.success(pageBean);
        } catch (Exception e) {

        }
        return Result.error("");
    }
}
