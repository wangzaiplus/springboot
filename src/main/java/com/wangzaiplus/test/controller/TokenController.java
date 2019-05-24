package com.wangzaiplus.test.controller;

import com.wangzaiplus.test.common.ServerResponse;
import com.wangzaiplus.test.pojo.User;
import com.wangzaiplus.test.service.TokenService;
import com.wangzaiplus.test.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/token")
public class TokenController {

    @Autowired
    private TokenService tokenService;

    @GetMapping
    public ServerResponse token() {
        return tokenService.createToken();
    }

}
