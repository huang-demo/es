package com.dem.es.repository;

import com.dem.es.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PersonJpaReponsitory extends JpaRepository<Person, Long> {
    /**
     * 相当于 select * from person where name=?1
     *
     * @param name
     * @return
     */
    List<Person> findByName(String name);

    /**
     * @param name
     * @return
     */
    @Query("select p from Person p where p.name = ?1")//hpql
    List<Person> findByName2(String name);

    /**
     * select *  from person where name like ?1
     *
     * @param name
     * @return
     */
    List<Person> findByNameLike(String name);

    /**
     * select * from person where name=?1 and address=?2
     *
     * @param name
     * @param address
     * @return
     */
    List<Person> findByNameAndAddress(String name, String address);

    List<Person> findByNameLikeAndAddressLike(String name, String address);

    @Query("select p from Person p where p.name like :name and p.address like :address")
    List<Person> findByNameLikeAndAddressLike2(@Param("name") String name, @Param("address") String address);

    /**
     * select *  from person where name like ?1 limit 2
     *
     * @param name
     * @return
     */
    List<Person> findFirst2ByNameLike(String name);
}
