package com.wangzaiplus.test.designpattern.factory.factorymethod;

import com.wangzaiplus.test.designpattern.factory.IPay;

public interface FactoryMethod {

    IPay createPay(String payType);

}
