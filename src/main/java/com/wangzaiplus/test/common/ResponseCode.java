package com.wangzaiplus.test.common;

/**
 * 响应状态码
 */
public enum ResponseCode {

    SUCCESS(0, "操作成功"),
    ERROR(1, "操作失败"),
    SERVER_ERROR(500, "服务器异常"),

    ILLEGAL_ARGUMENT(10000, "参数不合法"),
    NEED_LOGIN(10001, "登录失效"),
    REPETITIVE_OPERATION(10002, "请勿重复操作"),
    ACCESS_LIMIT(10003, "请求太频繁, 请稍后再试"),
    ;

    ResponseCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private Integer code;

    private String msg;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
