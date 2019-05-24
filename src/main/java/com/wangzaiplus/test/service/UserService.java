package com.wangzaiplus.test.service;

import com.wangzaiplus.test.pojo.User;

import java.util.List;

public interface UserService {

    List<User> getAll();

    User getOne(Integer id);

    void add(User user);

    void update(User user);

    void delete(Integer id);

    List<User> getByUsernameAndPassword(String username, String password);
}
