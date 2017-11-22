package com.dem.es.web;

import com.dem.es.domain.Person;
import com.dem.es.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/person")
public class DbPersonController {
    @Autowired
    private PersonService personService;


    @PostMapping("/findByName")
    public List<Person> findByName(String name) {
        return personService.findByName(name);
    }

    @PostMapping("/findByName2")
    public List<Person> findByName2(String name) {
        return personService.findByName2(name);
    }

    @PostMapping("/findByNameAndAddress")
    public List<Person> findByNameAndAddress(String name, String address) {
        return personService.findByNameAndAddress(name, address);
    }

    @PostMapping("/queryByName/{page}")
    public List<Person> queryByName(String name, @PathVariable Integer page) {
        return personService.findByNameLike(name, page, 3);
    }


    @PostMapping("/queryNameAndAddress")
    public List<Person> queryNameAndAddress(String name, String address) {
        return personService.queryByNameAndAddress(name, address);
    }

    @PostMapping("/queryNameAndAddress2")
    public List<Person> queryNameAndAddress2(String name, String address) {
        return personService.queryByNameAndAddress2(name, address);
    }

    @PostMapping("/queryByNameLimit2")
    public List<Person> queryByNameLimit2(String name) {
        return personService.queryByNameLimit2(name);
    }
}
