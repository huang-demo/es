package com.dem.es.service;

import com.dem.es.entity.po.Person;
import com.dem.es.entity.req.ElasticReq;
import com.dem.es.util.PageBean;

import java.util.List;

public interface PersonService {
    /**
     * 插入数据
     *
     * @param person
     * @return
     */
    Person insert(Person person);

    /**
     * 按名称查询 jpa
     *
     * @param name
     * @return
     */
    List<Person> findByName(String name);

    /**
     * 按名称查询 带排序
     *
     * @param name
     * @param sortFalg
     * @return
     */
    List<Person> findByNameLike(String name, boolean sortFalg);

    /**
     * 带分页模糊查询
     *
     * @param name
     * @param pageNo
     * @param pageSize
     * @return
     */
    List<Person> findByNameLike(String name, Integer pageNo, Integer pageSize);

    /**
     * 按名称hpql 查询
     *
     * @param name
     * @return
     */
    List<Person> findByName2(String name);

    /**
     * 按名称模糊查询
     *
     * @param name
     * @return
     */
    List<Person> findByNameLike(String name);

    /**
     * jpa 多字段精确查询
     *
     * @param name
     * @param address
     * @return
     */
    List<Person> findByNameAndAddress(String name, String address);

    /**
     * jpa 多字段模糊查询
     *
     * @param name
     * @param address
     * @return
     */
    List<Person> queryByNameAndAddress(String name, String address);

    /**
     * hpql 名称占位符查询
     *
     * @param name
     * @param address
     * @return
     */
    List<Person> queryByNameAndAddress2(String name, String address);

    /**
     * 按名称模糊匹配 返回结果限定2个
     *
     * @param name
     * @return
     */
    List<Person> queryByNameLimit2(String name);

    /**
     * 更新
     * @param id
     * @param person
     * @return
     */
    int update(Long id, Person person);

    int deleteByName(String name);

    Person get(Long id);

    int deleteById(Long id);

    PageBean queryPage(ElasticReq req);
}
