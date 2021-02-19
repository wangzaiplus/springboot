package com.wangzaiplus.test.controller;

import com.wangzaiplus.test.common.ServerResponse;
import com.wangzaiplus.test.dto.UserDto;
import com.wangzaiplus.test.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
@Slf4j
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/doLogin")
    public ServerResponse doLogin(@RequestBody UserDto userDto) {
        return loginService.doLogin(userDto);
    }

}
