package com.wangzaiplus.test.controller;

import com.google.common.collect.Lists;
import com.wangzaiplus.test.annotation.AccessLimit;
import com.wangzaiplus.test.annotation.ApiIdempotent;
import com.wangzaiplus.test.common.ServerResponse;
import com.wangzaiplus.test.mapper.MsgLogMapper;
import com.wangzaiplus.test.mapper.UserMapper;
import com.wangzaiplus.test.pojo.Mail;
import com.wangzaiplus.test.pojo.User;
import com.wangzaiplus.test.service.TestService;
import com.wangzaiplus.test.service.batch.mapperproxy.MapperProxy;
import com.wangzaiplus.test.util.MailUtil;
import com.wangzaiplus.test.util.RandomUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/test")
@Slf4j
public class TestController {

    @Autowired
    private TestService testService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MsgLogMapper msgLogMapper;

    @Autowired
    private MailUtil mailUtil;

    @ApiIdempotent
    @PostMapping("testIdempotence")
    public ServerResponse testIdempotence() {
        return testService.testIdempotence();
    }

    @AccessLimit(maxCount = 5, seconds = 5)
    @PostMapping("accessLimit")
    public ServerResponse accessLimit() {
        return testService.accessLimit();
    }

    @PostMapping("send")
    public ServerResponse sendMail(@Validated Mail mail, Errors errors) {
        if (errors.hasErrors()) {
            String msg = errors.getFieldError().getDefaultMessage();
            return ServerResponse.error(msg);
        }

        return testService.send(mail);
    }

    @PostMapping("single")
    public ServerResponse single(int size) {
        List<User> list = Lists.newArrayList();

        for (int i = 0; i < size; i++) {
            String str = RandomUtil.UUID32();
            User user = User.builder().username(str).password(str).build();
            list.add(user);
        }

        long startTime = System.nanoTime();
        log.info("batch insert costs: {} ms", (System.nanoTime() - startTime) / 1000000);

        return ServerResponse.success();
    }

    @PostMapping("batchInsert")
    public ServerResponse batchInsert(int size) {
        List<User> list = Lists.newArrayList();

        for (int i = 0; i < size; i++) {
            String str = RandomUtil.UUID32();
            User user = User.builder().username(str).password(str).build();
            list.add(user);
        }

        new MapperProxy<User>(userMapper).batchInsert(list);

        return ServerResponse.success();
    }

    @PostMapping("batchUpdate")
    public ServerResponse batchUpdate(String ids) {
        List<User> list = Lists.newArrayList();

        String[] split = ids.split(",");
        for (String id : split) {
            User user = User.builder().id(Integer.valueOf(id)).username("batchUpdate_" + RandomUtil.UUID32()).password("123456").build();
            list.add(user);
        }

        new MapperProxy<User>(userMapper).batchUpdate(list);

        return ServerResponse.success();
    }

    @PostMapping("sync")
    public ServerResponse sync() {
        List<User> list = Lists.newArrayList();
        for (int i = 0; i < 300; i++) {
            String uuid32 = RandomUtil.UUID32();
            User user = User.builder().username(uuid32).password(uuid32).build();
            list.add(user);
        }

        userMapper.batchInsert(list);

        check(list);

        return ServerResponse.success();
    }

    @Async
    public void check(List<User> list) {
        String username = list.get(list.size() - 1).getUsername();
        User user = userMapper.selectByUsername(username);
        log.info(user.getUsername());
    }

    @PostMapping("sendMail")
    public ServerResponse sendMail(@RequestBody Mail mail) {
        Mail build = Mail.builder().to(mail.getTo()).title(mail.getTitle()).content(mail.getContent()).build();
        boolean send = mailUtil.send(build);
        return ServerResponse.success(send);
    }

}
