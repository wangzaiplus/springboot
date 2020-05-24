package com.wangzaiplus.test.designpattern.factory.factorymethod;

import com.wangzaiplus.test.designpattern.factory.IPay;

public class BFactory implements FactoryMethod {

    @Override
    public IPay createPay(String payType) {
        return null;
    }

}
