package com.wangzaiplus.test.common;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

public class ServerResponse implements Serializable{

    private static final long serialVersionUID = 7498483649536881777L;

    private Integer status;

    private String msg;

    private Object data;

    public ServerResponse() {
    }

    public ServerResponse(Integer status, String msg, Object data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    @JsonIgnore
    public boolean isSuccess() {
        return this.status == ResponseCode.SUCCESS.getCode();
    }

    public static ServerResponse success() {
        return new ServerResponse(ResponseCode.SUCCESS.getCode(), null, null);
    }

    public static ServerResponse success(String msg) {
        return new ServerResponse(ResponseCode.SUCCESS.getCode(), msg, null);
    }

    public static ServerResponse success(Object data) {
        return new ServerResponse(ResponseCode.SUCCESS.getCode(), null, data);
    }

    public static ServerResponse success(String msg, Object data) {
        return new ServerResponse(ResponseCode.SUCCESS.getCode(), msg, data);
    }

    public static ServerResponse error(String msg) {
        return new ServerResponse(ResponseCode.ERROR.getCode(), msg, null);
    }

    public static ServerResponse error(Object data) {
        return new ServerResponse(ResponseCode.ERROR.getCode(), null, data);
    }

    public static ServerResponse error(String msg, Object data) {
        return new ServerResponse(ResponseCode.ERROR.getCode(), msg, data);
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
