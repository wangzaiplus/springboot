package com.wangzaiplus.test.amqp;

import com.rabbitmq.client.Channel;
import com.wangzaiplus.test.pojo.LoginLog;
import com.wangzaiplus.test.service.LoginLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class LoginLogConsumer implements BaseConsumer {

    @Autowired
    LoginLogService loginLogService;

    @Override
    public void consume(Message message, Channel channel) throws IOException {
        MessageProperties properties = message.getMessageProperties();
        long tag = properties.getDeliveryTag();

        try {
            log.info("收到消息: {}", message.toString());
            LoginLog loginLog = MessageHelper.msgToObj(message, LoginLog.class);
            loginLogService.insert(loginLog);
        } catch (Exception e){
            log.error("logUserConsumer error", e);
            channel.basicNack(tag, false, true);
        } finally {
            channel.basicAck(tag, false);
        }
    }
}
