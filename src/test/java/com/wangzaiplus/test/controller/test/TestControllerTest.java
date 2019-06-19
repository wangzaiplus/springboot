package com.wangzaiplus.test.controller.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void testLogin() {
        for (int i = 0; i < 100; i++) {
            testRestTemplate.postForObject("/user/login?username={username}&password={password}", null, String.class, "wangzaiplus", "123456");
        }
    }

}