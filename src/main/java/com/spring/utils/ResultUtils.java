package com.spring.utils;

import com.spring.enums.StatusCodeEnum;
import com.spring.entity.common.Result;

/*
* 服务端响应结果工具类
* */
public class ResultUtils {

    public static Result getResult(StatusCodeEnum statusCode, String msg, Object data) {
        int code = statusCode.getCode();
        return new Result(code, msg, data);
    }

}

