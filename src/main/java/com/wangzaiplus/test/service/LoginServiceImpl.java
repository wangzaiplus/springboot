package com.wangzaiplus.test.service;

import com.wangzaiplus.test.common.ResponseCode;
import com.wangzaiplus.test.common.ServerResponse;
import com.wangzaiplus.test.dto.UserDto;
import com.wangzaiplus.test.mapper.UserMapper;
import com.wangzaiplus.test.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class LoginServiceImpl implements LoginService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public ServerResponse doLogin(UserDto userDto) {
        String username = userDto.getUsername();
        String password = userDto.getPassword();

        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            return ServerResponse.error(ResponseCode.USERNAME_OR_PASSWORD_EMPTY.getMsg());
        }

        User user = userMapper.selectByUsernameAndPassword(username, password);
        if (null == user) {
            return ServerResponse.error(ResponseCode.USERNAME_OR_PASSWORD_WRONG.getMsg());
        }
        return null;
    }

}
