package com.wangzaiplus.test.config;

import com.wangzaiplus.test.util.ConfigUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class RabbitConfig {

    @Autowired
    private CachingConnectionFactory connectionFactory;

    @Bean
    public RabbitTemplate rabbitTemplate() {
        connectionFactory.setPublisherConfirms(true);
        connectionFactory.setPublisherReturns(true);

        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter());
        rabbitTemplate.setMandatory(true);// 使用return-callback时必须设置mandatory为true

        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            if (ack) {
                log.info("消息发送成功: correlationData: {}", correlationData);
            } else {
                log.info("消息发送失败: correlationData: {}, cause: {}", correlationData, cause);
            }
        });

        rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> {
            log.info("消息丢失: exchange: {}, route: {}, replyCode: {}, replyText: {}, message: {}", exchange, routingKey, replyCode, replyText, message);
        });

        return rabbitTemplate;
    }

    @Bean
    public Jackson2JsonMessageConverter converter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public Queue logUserQueue() {
        return new Queue(ConfigUtil.getValue("log.user.queue.name"), true);
    }

    @Bean
    public DirectExchange logUserExchange() {
        return new DirectExchange(ConfigUtil.getValue("log.user.exchange.name"), true, false);
    }

    @Bean
    public Binding logUserBinding() {
        return BindingBuilder.bind(logUserQueue()).to(logUserExchange()).with(ConfigUtil.getValue("log.user.routing.key.name"));
    }

}
