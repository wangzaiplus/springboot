package com.wangzaiplus.test.mq.listener;

import com.rabbitmq.client.Channel;
import com.wangzaiplus.test.config.RabbitConfig;
import com.wangzaiplus.test.mq.BaseConsumer;
import com.wangzaiplus.test.mq.BaseConsumerProxy;
import com.wangzaiplus.test.mq.consumer.LoginLogConsumer;
import com.wangzaiplus.test.service.MsgLogService;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class LoginLogListener {

    @Autowired
    private LoginLogConsumer loginLogConsumer;

    @Autowired
    private MsgLogService msgLogService;

    @RabbitListener(queues = RabbitConfig.LOGIN_LOG_QUEUE_NAME)
    public void consume(Message message, Channel channel) throws IOException {
        BaseConsumerProxy baseConsumerProxy = new BaseConsumerProxy(loginLogConsumer, msgLogService);
        BaseConsumer proxy = (BaseConsumer) baseConsumerProxy.getProxy();
        if (null != proxy) {
            proxy.consume(message, channel);
        }
    }

}
