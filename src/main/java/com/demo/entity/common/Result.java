package com.demo.entity.common;

import lombok.Data;

import java.io.Serializable;

/*
 * 响应结果
 * */
@Data
public class Result implements Serializable {
    /**
     * 响应码
     */
    private int code;

    /**
     * 响应消息
     */
    private String msg;

    private Object data;

    /*public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
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
    }*/

    public Result(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public Result() {
    }
}
