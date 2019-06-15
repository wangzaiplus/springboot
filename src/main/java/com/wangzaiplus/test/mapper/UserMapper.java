package com.wangzaiplus.test.mapper;

import com.wangzaiplus.test.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {

    List<User> selectAll();

    User selectOne(Integer id);

    void insert(User user);

    void update(User user);

    void delete(Integer id);

    User selectByUsernameAndPassword(@Param("username") String username, @Param("password") String password);
}
