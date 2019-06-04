package com.dem.es.web;

import com.dem.es.entity.po.Person;
import com.dem.es.entity.req.ElasticReq;
import com.dem.es.service.PersonService;
import com.dem.es.util.PageBean;
import com.dem.es.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/person")
@Api(description = "Person 控制器")
public class PersonController {
    @Autowired
    private PersonService personService;

    @ApiOperation(value = "插入用户数据")
    @PostMapping("/insert")
    public Result<Person> insert(String name, String address) {
        Person person = new Person();
        person.setName(name);
        person.setAddress(address);
        personService.insert(person);
        return Result.success(person);
    }

    @ApiOperation(value = "根据id 获取用户数据")
    @GetMapping("/{id}")
    public Result<Person> getById(@PathVariable Long id) {
        Person person = personService.get(id);
        return Result.success(person);
    }

    @ApiOperation("根据id更新数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", required = true, paramType = "Long"),
            @ApiImplicitParam(name = "person", required = true, paramType = "Person")
    })
    @PostMapping("/{id}")
    public Result<Integer> update(@PathVariable Long id, Person person) {
        int totalCount = personService.update(id, person);
        return Result.success(totalCount);
    }

    @ApiOperation(value = "/按名称精确查询 jpa自带", notes = "")
    @PostMapping("/findByName")
    public Result<List<Person>> findByName(String name) {
        return Result.success(personService.findByName(name));
    }

    @ApiOperation(value = "按名称精确查询使用hpql", notes = "")
    @PostMapping("/findByName2")
    public Result<List<Person>> findByName2(String name) {
        return Result.success(personService.findByName2(name));
    }

    @ApiOperation(value = "按名称和地址精确查询", notes = "")
    @PostMapping("/findByNameAndAddress")
    public Result<List<Person>> findByNameAndAddress(String name, String address) {
        return Result.success(personService.findByNameAndAddress(name, address));
    }

    @ApiOperation(value = "按名称模糊匹配,带分页默认一页3条", notes = "")
    @PostMapping("/queryByName/{page}")
    public Result<List<Person>> queryByName(String name, @PathVariable Integer page) {
        return Result.success(personService.findByNameLike(name, page, 3));
    }

    @ApiOperation(value = "按名称/地址模糊查询jpa自带", notes = "")
    @PostMapping("/queryNameAndAddress")
    public Result<List<Person>> queryNameAndAddress(String name, String address) {
        return Result.success(personService.queryByNameAndAddress(name, address));
    }

    @ApiOperation(value = "删除指定名称的用户数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", required = true, paramType = "String")
    })
    @DeleteMapping("/del")
    public Result<Integer> deleteByName(@RequestParam(required = true) String name) {
        return Result.success(personService.deleteByName(name));
    }

    @ApiOperation(value = "按名称/地址模糊查询 hpql 命名参数", notes = "")
    @PostMapping("/queryNameAndAddress2")
    public Result<List<Person>> queryNameAndAddress2(String name, String address) {
        return Result.success(personService.queryByNameAndAddress2(name, address));
    }

    @ApiOperation(value = "按名称模糊查询限制返回前两条", notes = "")
    @PostMapping("/queryByNameLimit2")
    public Result<List<Person>> queryByNameLimit2(String name) {
        return Result.success(personService.queryByNameLimit2(name));
    }

    @ApiOperation(value = "列表页面查询", notes = "列表页面查询")
    @PostMapping("/queryPage")
    public Result queryPage(ElasticReq req) {
        try {
            PageBean pageBean = personService.queryPage(req);
            return Result.success(pageBean);
        } catch (Exception e) {
        }
        return Result.error("");
    }
}
