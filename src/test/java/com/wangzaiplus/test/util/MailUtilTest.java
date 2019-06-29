package com.wangzaiplus.test.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MailUtilTest {

    @Autowired
    private MailUtil mailUtil;

    @Test
    public void send() {
        mailUtil.send("18621142249@163.com", "邮件标题", "邮件正文内容哦哦哦哦哦哦哦哦哦");
    }

    @Test
    public void sendAttachment() {
        File file = new File("C:/Users/wangzaiplus/Desktop/test测试.xlsx");
        mailUtil.sendAttachment("18621142249@163.com", "邮件标题", "邮件正文内容哦哦哦哦哦哦哦哦哦", file);
    }

}