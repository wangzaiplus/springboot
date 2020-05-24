package com.wangzaiplus.test.designpattern.factory.factorymethod;

import com.wangzaiplus.test.designpattern.factory.IPay;

public class PayService {

    public void pay(String payType) {
        FactoryMethod aFactory = new AFactory();
        IPay pay = aFactory.createPay(payType);

    }

}
