package com.spring.entity.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "token")
public class TokenProperties {
    //token有效时间，单位分钟
    private Integer tokenExpireTime;

    /**
     * token更新时间，单位分钟
     */
    private Integer tokenRefreshTime;

    //Shiro缓存有效期，单位分钟
    private Integer shiroCacheExpireTime;

    //token加密密钥
    private String secretKey;

    /*public Integer getTokenExpireTime() {
        return tokenExpireTime;
    }

    public void setTokenExpireTime(Integer tokenExpireTime) {
        this.tokenExpireTime = tokenExpireTime;
    }

    public Integer getTokenRefreshTime() {
        return tokenRefreshTime;
    }

    public void setTokenRefreshTime(Integer tokenRefreshTime) {
        this.tokenRefreshTime = tokenRefreshTime;
    }

    public Integer getShiroCacheExpireTime() {
        return shiroCacheExpireTime;
    }

    public void setShiroCacheExpireTime(Integer shiroCacheExpireTime) {
        this.shiroCacheExpireTime = shiroCacheExpireTime;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public TokenProperties() {
    }*/
}
