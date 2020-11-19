package com.wangzaiplus.test.mapper;

import com.wangzaiplus.test.pojo.Fund;
import com.wangzaiplus.test.pojo.User;
import com.wangzaiplus.test.service.batch.BatchProcessMapper;

import java.util.List;

public interface FundMapper extends BatchProcessMapper<Fund> {

    List<User> selectAll();

    User selectOne(Integer id);

    void insert(User user);

    void update(User user);

    void delete(Integer id);

}
