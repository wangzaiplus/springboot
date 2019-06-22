package com.wangzaiplus.test.service;

import com.wangzaiplus.test.pojo.LoginLog;

public interface LoginLogService {

    void insert(LoginLog loginLog);

    LoginLog selectByMsgId(String msgId);

}
