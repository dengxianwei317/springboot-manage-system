package com.demo.utils;

import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.demo.constant.StringConstant;
import com.demo.entity.properties.TokenProperties;
import com.demo.entity.system.SystemUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Date;

@Component
public class JwtUtils {

    @Autowired
    TokenProperties tokenProperties;

    static TokenProperties tokenEntity;

    @PostConstruct
    public void init() {
        tokenEntity = tokenProperties;
    }

    /**
     * 校验token是否正确
     */
    public static boolean checkToken(String token) {
        String secret = null;

        try {
            secret = getClaim(token, StringConstant.ACCOUNT) + tokenEntity.getSecretKey();
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm).build();
            verifier.verify(token);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    /**
     * 获得Token中的信息无需secret解密也能获得
     */
    public static String getClaim(String token, String claim) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim(claim).asString();
        } catch (JWTDecodeException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 生成签名,n分钟后过期
     */
    public static String createToken(SystemUser user, String currentTimeMillis) {
        String token = null;

        // 帐号加JWT私钥加密
        String secret = user.getAccount() + tokenEntity.getSecretKey();
        // 此处过期时间，单位：毫秒
        Date date = new Date(System.currentTimeMillis() + tokenEntity.getTokenExpireTime() * 60 * 1000l);
        Algorithm algorithm = Algorithm.HMAC256(secret);

        try {
            token = JWT.create()
                    .withClaim(StringConstant.ACCOUNT, user.getAccount())
                    .withClaim(StringConstant.USER_ID, user.getId())
                    .withClaim(StringConstant.USER_NAME, user.getUserName())
                    .withClaim(StringConstant.CURRENT_TIME_MILLIS, currentTimeMillis)
                    .withExpiresAt(date)
                    .sign(algorithm);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return token;
    }
}
