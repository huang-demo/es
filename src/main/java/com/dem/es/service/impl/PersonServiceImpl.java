package com.dem.es.service.impl;

import com.dem.es.domain.Person;
import com.dem.es.repository.PersonJpaReponsitory;
import com.dem.es.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PersonServiceImpl implements PersonService {
    @Autowired
    private PersonJpaReponsitory personJpaReponsitory;

    @Override
    @Transactional
    public Person insert(Person person) {
        return personJpaReponsitory.save(person);
    }

    @Override
    @Transactional
    public int update(Long id, Person person) {
        int count = personJpaReponsitory.update(id, person.getName(), person.getAddress());
        return count;
    }

    @Override
    @Transactional//注释在类级别上表示当前类的所有公共方法都添加事务
    public int deleteByName(String name) {
        return personJpaReponsitory.deleteByName(name);
    }

    @Override
    public List<Person> findByName(String name) {
        return personJpaReponsitory.findByName(name);
    }


    @Override
    public List<Person> findByNameLike(String name, boolean sortFalg) {
        if (StringUtils.hasLength(name)) {
            name = "%" + name + "%";
        }
        Sort sort = new Sort(sortFalg ? Sort.Direction.DESC : Sort.Direction.ASC, "id");
        return personJpaReponsitory.findByNameLike(name, sort);
    }

    @Override
    public List<Person> findByNameLike(String name, Integer pageNo, Integer pageSize) {
        if (StringUtils.hasLength(name)) {
            name = "%" + name + "%";
        }
        return personJpaReponsitory.findByNameLike(name, new PageRequest(pageNo, pageSize));
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

        return personJpaReponsitory.findByNameLikeAndAddressLike2(name, address);
    }

    @Override
    public List<Person> queryByNameLimit2(String name) {
        if (StringUtils.hasLength(name)) {
            name = "%" + name + "%";
        }
        return personJpaReponsitory.findFirst2ByNameLike(name);
    }
}
