package com.spring.utils;

import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;

public class ShiroUtils {
    /**
     * 加盐参数
     */
    public final static String hashAlgorithmName = "md5";

    /**
     * 循环次数
     */
    public final static int hashIterations = 2;

    /*
     * 获取加密后密码
     * */
    public static String getHashPassword(String password, String salt) {
        return new SimpleHash(hashAlgorithmName, password, salt, hashIterations).toString();
    }

    /*
    * 生成盐,默认长度 16 位
    * */
    public static String getSalt() {
        return new SecureRandomNumberGenerator().nextBytes().toString();
    }
}
