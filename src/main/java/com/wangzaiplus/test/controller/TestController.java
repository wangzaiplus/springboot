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
        log.info(jedisUtil.set("aaa", "aaa"));
        log.info(jedisUtil.set("bbb", "bbb", 10));
        log.info(jedisUtil.get("aaa"));

        log.info(jedisUtil.setObject("ccc", "ccc"));
        log.info(jedisUtil.setObject("ddd", "ddd", 30));
        log.info(jedisUtil.getObject("ccc").toString());

        log.info(jedisUtil.keys("ccc").toString());

        log.info(jedisUtil.expire("aaa", 30) + "");
        log.info(jedisUtil.ttl("aaa") + "");
        log.info(jedisUtil.exists("aaa") + "");
        log.info(jedisUtil.exists("aaaaaa") + "");

        return "hello world";
    }



}
