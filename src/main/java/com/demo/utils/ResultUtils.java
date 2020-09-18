package com.demo.utils;

import com.demo.enums.StatusCodeEnum;
import com.demo.entity.common.Result;

/*
* 服务端响应结果工具类
* */
public class ResultUtils {

    public static Result getResult(StatusCodeEnum statusCode, String msg, Object data) {
        int code = statusCode.getCode();
        return new Result(code, msg, data);
    }

}

