package com.wangzaiplus.test.controller;

import com.wangzaiplus.test.util.JedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
@Slf4j
public class TestController {

    @Autowired
    private JedisUtil jedisUtil;

    @RequestMapping("jedis")
    public String jedis() {
        log.info(jedisUtil.exists("aaa") + "");
        return "hello world";
    }

}
