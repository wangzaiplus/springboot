package com.wangzaiplus.test.designpattern.staticproxy;

import java.math.BigDecimal;

public class Test {

    public static void main(String[] args) {
        PayService payService = new PayServiceImpl();
        PayProxy payProxy = new PayProxy(payService);
        payProxy.pay("马云", BigDecimal.TEN);
    }

}
