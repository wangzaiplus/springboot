package com.wangzaiplus.test.service;

import com.wangzaiplus.test.common.ServerResponse;
import com.wangzaiplus.test.dto.UserDto;

public interface LoginService {

    ServerResponse doLogin(UserDto userDto);

}
