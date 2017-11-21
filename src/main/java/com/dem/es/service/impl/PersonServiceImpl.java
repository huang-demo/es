package com.dem.es.service.impl;

import com.dem.es.domain.Person;
import com.dem.es.repository.PersonJpaReponsitory;
import com.dem.es.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PersonServiceImpl implements PersonService {
    @Autowired
    private PersonJpaReponsitory personJpaReponsitory;


    @Override
    public List<Person> findByName(String name) {
        return personJpaReponsitory.findByName(name);
    }

    @Override
    public List<Person> findByName2(String name) {
        return personJpaReponsitory.findByName2(name);
    }

    @Override
    public List<Person> findByNameLike(String name) {
        if (StringUtils.hasLength(name)) {
            return personJpaReponsitory.findByNameLike("%" + name + "%");
        }
        return null;
    }

    @Override
    public List<Person> findByNameAndAddress(String name, String address) {

        return personJpaReponsitory.findByNameAndAddress(name, address);
    }


    @Override
    public List<Person> queryByNameAndAddress(String name, String address) {
        if (StringUtils.hasLength(name)) {
            name = "%" + name + "%";
        }
        if (StringUtils.hasLength(address)) {
            address = "%" + address + "%";
        }
        return personJpaReponsitory.findByNameLikeAndAddressLike(name, address);
    }

    @Override
    public List<Person> queryByNameAndAddress2(String name, String address) {
        if (StringUtils.hasLength(name)) {
            name = "%" + name + "%";
        }
        if (StringUtils.hasLength(address)) {
            address = "%" + address + "%";
        }

        return personJpaReponsitory.findByNameLikeAndAddressLike2(name,address);
    }

    @Override
    public List<Person> queryByNameLimit2(String name) {
        if (StringUtils.hasLength(name)) {
            name = "%" + name + "%";
        }
        return personJpaReponsitory.findFirst2ByNameLike(name);
    }
}
