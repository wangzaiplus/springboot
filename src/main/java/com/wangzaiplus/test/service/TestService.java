package com.wangzaiplus.test.service;

import com.wangzaiplus.test.common.ServerResponse;
import com.wangzaiplus.test.pojo.Mail;

public interface TestService {

    ServerResponse testIdempotence();

    ServerResponse accessLimit();

    ServerResponse send(Mail mail);
}
