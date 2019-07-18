package com.wangzaiplus.test.mq;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;

import java.io.IOException;

public interface BaseConsumer {

    void consume(Message message, Channel channel) throws IOException;

}
