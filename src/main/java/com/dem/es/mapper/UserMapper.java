package com.dem.es.mapper;

import com.dem.es.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;

@Mapper
public interface UserMapper {
    @Select("SELECT * FROM USER WHERE id = #{id}")
    User findById(@Param("id") Long id);

    @Insert("INSERT INTO USER(userName,passWord,email,sex,birthday,createDate}) VALUES(#{userName},#{passWord},#{email},#{sex},#{birthday},#{createDate})")
    int insert(@Param("userName") String userName, @Param("passWord") String passWord, String email, int sex, Date birthDay,Date createDate);
}
