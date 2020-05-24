package com.wangzaiplus.test.designpattern.strategy;

import com.wangzaiplus.test.designpattern.factory.PayTypeEnum;

public class Main {

    public static void main(String[] args) {
        PayContext payContext = new PayContext();
        payContext.pay("3", "aaa");
    }

}
