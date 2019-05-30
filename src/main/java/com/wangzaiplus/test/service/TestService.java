package com.wangzaiplus.test.service;

import com.wangzaiplus.test.common.ServerResponse;

public interface TestService {

    ServerResponse testIdempotence();

    ServerResponse accessLimit();

}
