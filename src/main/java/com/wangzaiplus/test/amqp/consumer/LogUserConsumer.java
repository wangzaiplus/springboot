package com.wangzaiplus.test.amqp.consumer;

import com.wangzaiplus.test.pojo.UserLog;
import com.wangzaiplus.test.service.UserLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class LogUserConsumer {

    @Autowired
    UserLogService userLogService;

    @RabbitListener(queues = "log.user.queue")
    public void logUserConsumer(UserLog userLog) {
        log.info("消费者1消费消息: {}", userLog.toString());
        userLogService.insert(userLog);
    }

}
