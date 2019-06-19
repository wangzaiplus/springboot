package com.wangzaiplus.test.amqp.consumer;

import com.rabbitmq.client.Channel;
import com.wangzaiplus.test.config.RabbitConfig;
import com.wangzaiplus.test.pojo.UserLog;
import com.wangzaiplus.test.service.UserLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class LogUserConsumer {

    @Autowired
    UserLogService userLogService;

    @RabbitListener(queues = RabbitConfig.LOG_USER_QUEUE_NAME)
    public void logUserConsumer(Message message, Channel channel, @Header (AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {
        try {
            log.info("收到消息: {}", message.toString());
            userLogService.insert(MessageHelper.msgToObj(message, UserLog.class));
        } catch (Exception e){
            log.error("logUserConsumer error", e);
            channel.basicNack(tag, false, true);
        } finally {
            channel.basicAck(tag, false);
        }
    }

}
