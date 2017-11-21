package com.dem.es.service;

import com.dem.es.domain.Person;

import java.util.List;

public interface PersonService {
    List<Person> findByName(String name);

    List<Person> findByName2(String name);

    List<Person> findByNameLike(String name);

    List<Person> findByNameAndAddress(String name, String address);

    List<Person> queryByNameAndAddress(String name, String address);

    List<Person> queryByNameAndAddress2(String name, String address);

    List<Person> queryByNameLimit2(String name);
}
