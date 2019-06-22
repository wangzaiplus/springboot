package com.wangzaiplus.test.amqp;

import com.rabbitmq.client.Channel;
import com.wangzaiplus.test.config.RabbitConfig;
import com.wangzaiplus.test.pojo.LoginLog;
import com.wangzaiplus.test.service.LoginLogService;
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
    LoginLogService loginLogService;

    @RabbitListener(queues = RabbitConfig.LOGIN_LOG_QUEUE_NAME)
    public void logUserConsumer(Message message, Channel channel, @Header (AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {
        try {
            log.info("收到消息: {}", message.toString());

            LoginLog loginLog = MessageHelper.msgToObj(message, LoginLog.class);
            LoginLog lLog = loginLogService.selectByMsgId(loginLog.getMsgId());
            if (null == lLog) {// 消费幂等性
                loginLogService.insert(loginLog);
            }
        } catch (Exception e){
            log.error("logUserConsumer error", e);
            channel.basicNack(tag, false, true);
        } finally {
            channel.basicAck(tag, false);
        }
    }

}
