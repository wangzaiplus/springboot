package com.wangzaiplus.test.amqp;

import com.rabbitmq.client.Channel;
import com.wangzaiplus.test.config.RabbitConfig;
import com.wangzaiplus.test.util.JedisUtil;
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
    private JedisUtil jedisUtil;

    @RabbitListener(queues = RabbitConfig.LOGIN_LOG_QUEUE_NAME)
    public void consume(Message message, Channel channel) throws IOException {
        System.out.println("consume");
        ConsumerProxy consumerProxy = new ConsumerProxy(loginLogConsumer, jedisUtil);
        BaseConsumer proxy = (BaseConsumer) consumerProxy.getProxy();
        if (null != proxy) {
            proxy.consume(message, channel);
        }
    }

    @RabbitListener(queues = RabbitConfig.LOGIN_LOG_QUEUE_NAME)
    public void consume2(Message message, Channel channel) throws IOException {
        System.out.println("consume2");
        ConsumerProxy consumerProxy = new ConsumerProxy(loginLogConsumer, jedisUtil);
        BaseConsumer proxy = (BaseConsumer) consumerProxy.getProxy();
        if (null != proxy) {
            proxy.consume(message, channel);
        }
    }

}
