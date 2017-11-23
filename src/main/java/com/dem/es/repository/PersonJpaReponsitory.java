package com.dem.es.repository;

import com.dem.es.domain.Person;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
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
    @Query("select p from Person p where p.name = ?1")
    List<Person> findByName2(String name);

    /**
     * select *  from person where name like ?1
     *
     * @param name
     * @return
     */
    List<Person> findByNameLike(String name);

    /**
     * 排序
     *
     * @param name
     * @param sort
     * @return
     */
    List<Person> findByNameLike(String name, Sort sort);

    /**
     * 带分页查询
     *
     * @param name
     * @param pageable
     * @return
     */
    List<Person> findByNameLike(String name, Pageable pageable);

    /**
     * select * from person where name=?1 and address=?2
     *
     * @param name
     * @param address
     * @return
     */
    List<Person> findByNameAndAddress(String name, String address);

    /**
     * @param name
     * @param address
     * @return
     */
    List<Person> findByNameLikeAndAddressLike(String name, String address);

    /**
     * @param name
     * @param address
     * @return
     */
    @Query("select p from Person p where p.name like :name and p.address like :address")
    List<Person> findByNameLikeAndAddressLike2(@Param("name") String name, @Param("address") String address);

    /**
     * select *  from person where name like ?1 limit 2
     *
     * @param name
     * @return
     */
    List<Person> findFirst2ByNameLike(String name);

    @Modifying//update delete 必须贴上此标签
    @Query("update Person p set p.name = :name,p.address=:address where p.id=:id")
    int update(@Param("id") Long id, @Param("name") String name, @Param("address") String address);

    @Modifying
    @Query("delete from Person p where p.name = :name")
    int deleteByName(@Param("name") String name);
}
