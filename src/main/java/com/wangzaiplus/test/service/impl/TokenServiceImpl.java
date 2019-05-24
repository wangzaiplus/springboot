package com.wangzaiplus.test.service.impl;

import com.wangzaiplus.test.common.Constant;
import com.wangzaiplus.test.common.ResponseCode;
import com.wangzaiplus.test.common.ServerResponse;
import com.wangzaiplus.test.exception.ServiceException;
import com.wangzaiplus.test.service.TokenService;
import com.wangzaiplus.test.util.JedisUtil;
import com.wangzaiplus.test.util.RandomUtil;
import org.apache.commons.lang3.text.StrBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TokenServiceImpl implements TokenService {

    @Autowired
    private JedisUtil jedisUtil;

    @Override
    public ServerResponse createToken() {
        String str = RandomUtil.generateStr(24);
        StrBuilder token = new StrBuilder();
        token.append(Constant.Redis.TOKEN_PREFIX).append(str);

        jedisUtil.set(token.toString(), token.toString(), Constant.Redis.EXPIRE_TIME_MINUTE);

        return ServerResponse.success(token.toString());
    }

}
