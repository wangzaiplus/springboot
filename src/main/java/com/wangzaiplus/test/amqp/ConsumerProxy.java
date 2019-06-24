package com.wangzaiplus.test.amqp;

import com.wangzaiplus.test.common.Constant;
import com.wangzaiplus.test.util.JedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;

import java.lang.reflect.Proxy;
import java.util.Map;

@Slf4j
public class ConsumerProxy {

    private Object target;

    private JedisUtil jedisUtil;

    public ConsumerProxy(Object target, JedisUtil jedisUtil) {
        this.target = target;
        this.jedisUtil  = jedisUtil;
    }

    public Object getProxy() {
        ClassLoader classLoader = target.getClass().getClassLoader();
        Class[] interfaces = target.getClass().getInterfaces();

        Object proxy = Proxy.newProxyInstance(classLoader, interfaces, (proxy1, method, args) -> {
            Message message = (Message) args[0];
            if (isConsumed(message)) {// 保证消费幂等性, 避免消息被重复消费
                log.info("重复消费, correlationId: {}", getCorrelationId(message));
                return null;
            }

            Object result = method.invoke(target, args);
            return result;
        });

        return proxy;
    }

    /**
     * 消息是否已被消费
     * @param message
     * @return
     */
    private boolean isConsumed(Message message) {
        if (null == message) {
            return true;
        }

        String correlationId = getCorrelationId(message);
        if (StringUtils.isBlank(correlationId)) {
            return true;
        }

        Long del = jedisUtil.del(Constant.Redis.MSG_CONSUMER_PREFIX + correlationId);
        if (del <= 0) {// 删除失败, 说明已消费
            return true;
        }

        return false;
    }

    /**
     * 获取CorrelationId
     * @param message
     * @return
     */
    private String getCorrelationId(Message message) {
        String correlationId = null;
        MessageProperties properties = message.getMessageProperties();
        Map<String, Object> headers = properties.getHeaders();
        for (Map.Entry entry : headers.entrySet()) {
            String key = (String) entry.getKey();
            String value = (String) entry.getValue();
            if (key.equals("spring_returned_message_correlation")) {
                correlationId = value;
            }
        }

        return correlationId;
    }

}
