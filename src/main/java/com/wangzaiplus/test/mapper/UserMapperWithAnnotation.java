package com.wangzaiplus.test.mapper;

import com.wangzaiplus.test.pojo.User;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

public interface UserMapperWithAnnotation {

    @Select("select * from user")
    @Results({
            @Result(property = "username", column = "username", jdbcType = JdbcType.VARCHAR),
            @Result(property = "password", column = "password")
    })
    List<User> selectAll();

    @Select("select * from user where id = #{id}")
    @Results({
            @Result(property = "username", column = "username", jdbcType = JdbcType.VARCHAR),
            @Result(property = "password", column = "password")
    })
    User selectOne(Integer id);

    @Insert("insert into user(username, password) values(#{username}, #{password})")
    void insert(User user);

    @Update("update user set username=#{username}, password=#{password} where id = #{id}")
    void update(User user);

    @Delete("delete from user where id = #{id}")
    void delete(Integer id);

}
