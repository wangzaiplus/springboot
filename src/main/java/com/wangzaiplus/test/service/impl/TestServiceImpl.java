package com.wangzaiplus.test.service.impl;

import com.wangzaiplus.test.common.ServerResponse;
import com.wangzaiplus.test.service.TestService;
import org.springframework.stereotype.Service;

@Service
public class TestServiceImpl implements TestService {

    @Override
    public ServerResponse testIdempotence() {
        return ServerResponse.success("操作成功");
    }

}
