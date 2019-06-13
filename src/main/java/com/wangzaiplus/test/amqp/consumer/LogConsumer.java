package com.wangzaiplus.test.amqp.consumer;

import com.wangzaiplus.test.config.RabbitConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class LogConsumer {

    @RabbitListener(queues = RabbitConfig.LOG_QUEUE_NAME)
    public void consumerLog(String msg) {
        log.info("消费日志信息: {}", msg);
    }

    @RabbitListener(queues = RabbitConfig.LOG_QUEUE_NAME)
    public void consumerLog2(String msg) {
        log.info("消费日志信息2: {}", msg);
    }

}
