package com.wangzaiplus.test.designpattern.staticproxy;

import java.math.BigDecimal;

public class PayProxy implements PayService {

    private PayService payService;

    public PayProxy(PayService payService) {
        this.payService = payService;
    }

    @Override
    public void pay(String username, BigDecimal money) {
        System.out.println(username + "发起支付...");

        payService.pay(username, money);

        System.out.println("支付状态: 成功");
    }

}
