package com.wangzaiplus.test.designpattern.factory;

/**
 * 支付方式
 */
public enum PayTypeEnum {

    ALI_PAY("1", "支付宝支付"),
    WE_CHAT_PAY("2", "微信支付"),
    UNION_PAY("3", "银联支付"),
    ;

    private String code;
    private String msg;

    PayTypeEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
