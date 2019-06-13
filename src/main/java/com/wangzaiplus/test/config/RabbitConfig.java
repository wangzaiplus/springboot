package com.wangzaiplus.test.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    // 直连交换机
    public static final String DIRECT_EXCHANGE_NAME = "direct.exchange";
    // 主题订阅交换机
    public static final String TOPIC_EXCHANGE_NAME = "topic.exchange";
    // 广播交换机
    public static final String FANOUT_EXCHANGE_NAME = "fanout.exchange";

    // 日志队列
    public static final String LOG_QUEUE_NAME = "log.queue";
    // 日志路由
    public static final String LOG_ROUTING_KEY_NAME = "log.routing.key";

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(DIRECT_EXCHANGE_NAME, true, false);
    }

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(TOPIC_EXCHANGE_NAME, true, false);
    }

    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange(FANOUT_EXCHANGE_NAME, true, false);
    }

    @Bean
    public Queue logQueue() {
        return new Queue(LOG_QUEUE_NAME, true);
    }

    @Bean
    public Binding logBinding() {
        return BindingBuilder.bind(logQueue()).to(directExchange()).with(LOG_ROUTING_KEY_NAME);
    }

}
