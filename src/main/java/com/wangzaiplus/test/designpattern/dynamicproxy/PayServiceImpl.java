package com.wangzaiplus.test.designpattern.dynamicproxy;

import java.math.BigDecimal;

public class PayServiceImpl implements PayService {

    @Override
    public void pay(String username, BigDecimal money) {
        System.out.println(username + "支付了" + money + "元钱");
    }

    @Override
    public void a() {
        System.out.println("a");
    }

    @Override
    public void b() {
        System.out.println("b");
    }

}