package com.wangzaiplus.test.service.impl;

import com.wangzaiplus.test.mapper.UserLogMapper;
import com.wangzaiplus.test.pojo.UserLog;
import com.wangzaiplus.test.service.UserLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserLogServiceImpl implements UserLogService {

    @Autowired
    private UserLogMapper userLogMapper;

    @Override
    public void insert(UserLog userLog) {
        userLogMapper.insert(userLog);
    }

}
