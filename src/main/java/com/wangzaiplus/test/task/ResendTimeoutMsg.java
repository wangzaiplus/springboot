package com.wangzaiplus.test.task;

import com.wangzaiplus.test.amqp.MessageHelper;
import com.wangzaiplus.test.common.Constant;
import com.wangzaiplus.test.config.RabbitConfig;
import com.wangzaiplus.test.mapper.MsgLogMapper;
import com.wangzaiplus.test.pojo.LoginLog;
import com.wangzaiplus.test.pojo.MsgLog;
import com.wangzaiplus.test.util.JodaTimeUtil;
import com.wangzaiplus.test.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
@Slf4j
public class ResendTimeoutMsg {

    @Autowired
    private MsgLogMapper msgLogMapper;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Scheduled(cron = "0/10 * * * * ?")
    public void resendTimeoutMsg() {
        log.info("开始执行定时任务...");

        List<MsgLog> msgLogs = msgLogMapper.selectTimeoutMsg();
        msgLogs.forEach(msgLog -> {
            String msgId = msgLog.getMsgId();
            if (msgLog.getTryCount() >= 3) {
                MsgLog mLog = new MsgLog();
                mLog.setMsgId(msgId);
                mLog.setStatus(Constant.MsgLogStatus.FAIL);
                msgLogMapper.updateStatus(mLog);

                log.info("超过最大重试次数, 消息投递失败, msgId: {}", msgId);
            } else {
                MsgLog mLog = new MsgLog();
                mLog.setMsgId(msgId);
                mLog.setNextTryTime(JodaTimeUtil.plusMinutes(msgLog.getNextTryTime(), 1));
                msgLogMapper.updateTryCount(mLog);

                CorrelationData correlationData = new CorrelationData();
                correlationData.setId(msgId);
                rabbitTemplate.convertAndSend(RabbitConfig.LOGIN_LOG_EXCHANGE_NAME, RabbitConfig.LOGIN_LOG_ROUTING_KEY_NAME, MessageHelper.objToMsg(JsonUtil.strToObj(msgLog.getMsg(), LoginLog.class)), correlationData);

                log.info("第 " + (msgLog.getTryCount() + 1) + " 次重新投递消息");
            }
        });

        log.info("定时任务结束");
    }

}
