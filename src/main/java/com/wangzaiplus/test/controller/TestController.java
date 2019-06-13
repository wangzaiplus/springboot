package com.wangzaiplus.test.controller;

import com.wangzaiplus.test.annotation.AccessLimit;
import com.wangzaiplus.test.annotation.ApiIdempotent;
import com.wangzaiplus.test.common.ServerResponse;
import com.wangzaiplus.test.config.RabbitConfig;
import com.wangzaiplus.test.service.TestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
@Slf4j
public class TestController {

    @Autowired
    private TestService testService;

    @Autowired
    private AmqpTemplate rabbitTemplate;

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

    @GetMapping("testRabbitMq")
    public ServerResponse testRabbitMq() {
        for (int i=0; i<10; i++) {
            rabbitTemplate.convertAndSend(RabbitConfig.DIRECT_EXCHANGE_NAME, RabbitConfig.LOG_ROUTING_KEY_NAME, "log msg: " + i);
        }
        return ServerResponse.success("send msg success");
    }

}
