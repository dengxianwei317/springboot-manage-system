package com.demo.enums;

/*
 * 状态码
 * */
public enum StatusCodeEnum {
    _200(200, "请求成功"),
    _400(400, "token无效,请求错误"),
    _401(401, "未经授权,非法请求"),
    _404(404, "请求的资源不存在"),
    _500(500, "服务器错误"),
    _800(800, "没有数据"),

    ;

    /**
     * 响应状态码
     */
    private final int code;

    /**
     * 响应提示
     */
    private final String msg;

    StatusCodeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
