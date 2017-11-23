package com.dem.es.web;

import com.dem.es.domain.Person;
import com.dem.es.service.PersonService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/person")
@Api(value = "数据库用户查询")
public class DbPersonController {
    @Autowired
    private PersonService personService;

    @ApiOperation(value = "插入用户数据")
    @PostMapping("/insert")
    public Person insert(String name, String address) {
        Person person = new Person();
        person.setName(name);
        person.setAddress(address);
        personService.insert(person);
        return person;
    }

    @ApiOperation("根据id更新数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", required = true, paramType = "Long"),
            @ApiImplicitParam(name = "person", required = true, paramType = "Person")
    })
    @PutMapping("/{id}")
    public int update(@PathVariable Long id, Person person) {
        int totalCount = personService.update(id, person);
        return totalCount;
    }

    @ApiOperation(value = "/按名称精确查询 jpa自带", notes = "")
    @PostMapping("/findByName")
    public List<Person> findByName(String name) {
        return personService.findByName(name);
    }

    @ApiOperation(value = "按名称精确查询使用hpql", notes = "")
    @PostMapping("/findByName2")
    public List<Person> findByName2(String name) {
        return personService.findByName2(name);
    }

    @ApiOperation(value = "按名称和地址精确查询", notes = "")
    @PostMapping("/findByNameAndAddress")
    public List<Person> findByNameAndAddress(String name, String address) {
        return personService.findByNameAndAddress(name, address);
    }

    @ApiOperation(value = "按名称模糊匹配,带分页默认一页3条", notes = "")
    @PostMapping("/queryByName/{page}")
    public List<Person> queryByName(String name, @PathVariable Integer page) {
        return personService.findByNameLike(name, page, 3);
    }

    @ApiOperation(value = "按名称/地址模糊查询jpa自带", notes = "")
    @PostMapping("/queryNameAndAddress")
    public List<Person> queryNameAndAddress(String name, String address) {
        return personService.queryByNameAndAddress(name, address);
    }

    @ApiOperation(value = "删除指定名称的用户数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name="name",required = true,paramType = "String")
    })
    @DeleteMapping("/del")
    public int deleteByName(@RequestParam(required = true) String name) {
        return personService.deleteByName(name);
    }

    @ApiOperation(value = "按名称/地址模糊查询 hpql 命名参数", notes = "")
    @PostMapping("/queryNameAndAddress2")
    public List<Person> queryNameAndAddress2(String name, String address) {
        return personService.queryByNameAndAddress2(name, address);
    }

    @ApiOperation(value = "按名称模糊查询限制返回前两条", notes = "")
    @PostMapping("/queryByNameLimit2")
    public List<Person> queryByNameLimit2(String name) {
        return personService.queryByNameLimit2(name);
    }
}
