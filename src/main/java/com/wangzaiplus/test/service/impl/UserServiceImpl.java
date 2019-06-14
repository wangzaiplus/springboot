package com.wangzaiplus.test.service.impl;

import com.wangzaiplus.test.common.Constant;
import com.wangzaiplus.test.common.ResponseCode;
import com.wangzaiplus.test.common.ServerResponse;
import com.wangzaiplus.test.mapper.UserLogMapper;
import com.wangzaiplus.test.mapper.UserMapper;
import com.wangzaiplus.test.pojo.User;
import com.wangzaiplus.test.pojo.UserLog;
import com.wangzaiplus.test.service.UserService;
import com.wangzaiplus.test.util.ConfigUtil;
import com.wangzaiplus.test.util.JodaTimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public List<User> getAll() {
        return userMapper.selectAll();
    }

    @Override
    public User getOne(Integer id) {
        return userMapper.selectOne(id);
    }

    @Override
    public void add(User user) {
        userMapper.insert(user);
    }

    @Override
    public void update(User user) {
        userMapper.update(user);
    }

    @Override
    public void delete(Integer id) {
        userMapper.delete(id);
    }

    @Override
    public User getByUsernameAndPassword(String username, String password) {
        return userMapper.selectByUsernameAndPassword(username, password);
    }

    @Override
    public ServerResponse login(String username, String password) {
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            return ServerResponse.error(ResponseCode.USERNAME_OR_PASSWORD_EMPTY.getMsg());
        }

        User user = userMapper.selectByUsernameAndPassword(username, password);
        if (null == user) {
            return ServerResponse.error(ResponseCode.USERNAME_OR_PASSWORD_WRONG.getMsg());
        }

        UserLog userLog = new UserLog();
        userLog.setUserId(user.getId());
        userLog.setType(Constant.LogType.LOGIN);
        userLog.setDescription(username + "在" + JodaTimeUtil.dateToStr(new Date()) + "登录系统");
        userLog.setCreateTime(new Date());
        userLog.setUpdateTime(new Date());

        rabbitTemplate.convertAndSend(ConfigUtil.getValue("log.user.exchange.name"), ConfigUtil.getValue("log.user.routing.key.name"), userLog);

        return ServerResponse.success(ResponseCode.SUCCESS);
    }

}
