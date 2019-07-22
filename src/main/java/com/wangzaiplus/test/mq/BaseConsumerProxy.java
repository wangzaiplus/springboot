package com.wangzaiplus.test.mq;

import com.rabbitmq.client.Channel;
import com.wangzaiplus.test.common.Constant;
import com.wangzaiplus.test.pojo.MsgLog;
import com.wangzaiplus.test.service.MsgLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;

import java.lang.reflect.Proxy;
import java.util.Map;

@Slf4j
public class BaseConsumerProxy {

    private Object target;

    private MsgLogService msgLogService;

    public BaseConsumerProxy(Object target, MsgLogService msgLogService) {
        this.target = target;
        this.msgLogService = msgLogService;
    }

    public Object getProxy() {
        ClassLoader classLoader = target.getClass().getClassLoader();
        Class[] interfaces = target.getClass().getInterfaces();

        Object proxy = Proxy.newProxyInstance(classLoader, interfaces, (proxy1, method, args) -> {
            Message message = (Message) args[0];
            Channel channel = (Channel) args[1];

            String correlationId = getCorrelationId(message);

            if (isConsumed(correlationId)) {// 消费幂等性, 防止消息被重复消费
                log.info("重复消费, correlationId: {}", correlationId);
                return null;
            }

            MessageProperties properties = message.getMessageProperties();
            long tag = properties.getDeliveryTag();

            try {
                Object result = method.invoke(target, args);// 真正消费的业务逻辑
                msgLogService.updateStatus(correlationId, Constant.MsgLogStatus.CONSUMED_SUCCESS);
                channel.basicAck(tag, false);// 消费确认
                return result;
            } catch (Exception e) {
                log.error("getProxy error", e);
                channel.basicNack(tag, false, true);
                return null;
            }
        });

        return proxy;
    }

    /**
     * 获取CorrelationId
     *
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

    /**
     * 消息是否已被消费
     *
     * @param correlationId
     * @return
     */
    private boolean isConsumed(String correlationId) {
        MsgLog msgLog = msgLogService.selectByMsgId(correlationId);
        if (null == msgLog || msgLog.getStatus().equals(Constant.MsgLogStatus.CONSUMED_SUCCESS)) {
            return true;
        }

        return false;
    }

}
