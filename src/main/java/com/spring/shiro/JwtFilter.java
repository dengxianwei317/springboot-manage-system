package com.spring.shiro;

import com.spring.constant.ExpireTimeConstant;
import com.spring.enums.StatusCodeEnum;
import com.spring.constant.StringConstant;
import com.spring.entity.common.Result;
import com.spring.entity.properties.TokenProperties;
import com.spring.entity.system.SystemUser;
import com.spring.service.system.SysUserService;
import com.spring.utils.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class JwtFilter extends BasicHttpAuthenticationFilter {

    //过滤器优先bean加载之前，因此无法注入
    TokenProperties tokenProperties;
    SysUserService userService;

    public JwtFilter(SysUserService userService, TokenProperties tokenProperties) {
        this.tokenProperties = tokenProperties;
        this.userService = userService;
    }

    /**
     * 检测Header里token字段
     */
    @Override
    protected boolean isLoginAttempt(ServletRequest servletRequest, ServletResponse servletResponse) {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String token = request.getHeader(StringConstant.REQUEST_HEADER);
        return token != null;
    }

    /**
     * 登录验证
     */
    @Override
    protected boolean executeLogin(ServletRequest servletRequest, ServletResponse response) throws Exception {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String token = request.getHeader(StringConstant.REQUEST_HEADER);

        if (!JwtUtils.checkToken(token))
            response400(response);
        else {
            Subject subject = getSubject(servletRequest, response);
            subject.login(new JwtToken(token));

            String cacheUserKey = StringConstant.CACHE_USER + JwtUtils.getClaim(token, StringConstant.ACCOUNT);
            if (!RedisUtils.exists(cacheUserKey)) {
                String id = JwtUtils.getClaim(token, StringConstant.USER_ID);
                SystemUser user = userService.getEntity(id);
                RedisUtils.set(cacheUserKey, user, tokenProperties.getTokenExpireTime());
            }

            //检查是否需要更换token，需要则重新颁发
            isRefreshToken(token, response);
        }
        return true;
    }

    /**
     * 检查是否需要刷新Token
     */
    private void isRefreshToken(String token, ServletResponse response) throws Exception {
        long currentTimeMillis = System.currentTimeMillis();
        String tokenMillis = JwtUtils.getClaim(token, StringConstant.CURRENT_TIME_MILLIS);
        String account = JwtUtils.getClaim(token, StringConstant.ACCOUNT);
        String refreshTokenKey = StringConstant.REFRESH_TOKEN + account;
        Date date = new Date(Long.valueOf(tokenMillis));

        //token时间戳不存在，需要返回登录页面
        if (!RedisUtils.exists(refreshTokenKey)) {
            response400(response);
        }
        //时间差大于token有效期，需要返回登录页面
        else if (currentTimeMillis - Long.parseLong(tokenMillis) > (tokenProperties.getTokenExpireTime() * 60 * 1000L)) {
            response400(response);
        }//时间差大于需要刷新时间，则生成新token返回给前端，更新redis中的token时间戳
        else if (currentTimeMillis - Long.parseLong(tokenMillis) >= (tokenProperties.getTokenRefreshTime() * 60 * 1000L)) {
            String lockName = StringConstant.LOCK_NAME + account;
            boolean b = LockUtils.getLock(lockName, ExpireTimeConstant.ONE_MINUTE);

            if (b) {
                //获取到锁
                String tokenTimeStamp = String.valueOf(RedisUtils.get(refreshTokenKey));
                //检查redis中的时间戳与token的时间戳是否一致
                if (!tokenMillis.equals(tokenTimeStamp)) {//令牌无效,返回登录页面
                    RedisUtils.delete(refreshTokenKey);
                    response400(response);
                } else {//生成新token返回给前端，更新redis中的token时间戳
                    String userId = JwtUtils.getClaim(token, StringConstant.USER_ID);
                    SystemUser user = userService.getById(userId);
                    String newToken = JwtUtils.createToken(user, String.valueOf(currentTimeMillis));

                    RedisUtils.set(refreshTokenKey, String.valueOf(currentTimeMillis), tokenProperties.getTokenExpireTime() * 60);

                    response.setCharacterEncoding("UTF-8");
                    response.setContentType("application/json; charset=utf-8");

                    ServletOutputStream outputStream = response.getOutputStream();

                    Map<String, Object> mapParams = new HashMap<>();
                    mapParams.put(StringConstant.REQUEST_HEADER, newToken);
                    mapParams.put(StringConstant.EXPIRE_TIME, tokenProperties.getTokenExpireTime() * 60);
                    user = new SystemUser();
                    user.setId(UserContext.getCurrentUser().getId());
                    user.setAccount(UserContext.getCurrentUser().getAccount());
                    user.setUserName(UserContext.getCurrentUser().getUserName());
                    user.setListRole(UserContext.getCurrentUser().getListRole());
                    mapParams.put("user", user);

                    Result result = ResultUtils.getResult(StatusCodeEnum._200, StatusCodeEnum._200.getMsg(), mapParams);
                    outputStream.write(JsonUtils.toJsonString(result).getBytes());
                    outputStream.flush();
                    outputStream.close();
                }
            }
            LockUtils.releaseLock(lockName);
        }
    }

    /**
     * 是否允许访问
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        try {
            if (isLoginAttempt(request, response)) {
                executeLogin(request, response);
            } else {
                response400(response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }

    /**
     * 重写 onAccessDenied 方法，避免父类中调用再次executeLogin
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) {
        this.sendChallenge(request, response);
        return false;
    }

    /*
     * 重写阻止socket关闭后再次请求
     * */
    @Override
    public void doFilterInternal(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        Exception exception = null;

        try {
            boolean continueChain = this.preHandle(request, response);

            if (continueChain) {
                this.executeChain(request, response, chain);
            }
        } catch (Exception ex) {
            exception = ex;
        } finally {
            this.cleanup(request, response, exception);
        }
    }

    /**
     * 401非法请求
     */
    private void response400(ServletResponse response) throws Exception {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");

        Result result = null;
        ServletOutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
            result = ResultUtils.getResult(StatusCodeEnum._400, StatusCodeEnum._400.getMsg(), null);
            outputStream.write(JsonUtils.toJsonString(result).getBytes());
            outputStream.flush();

        } catch (IOException e) {
            log.error("返回Response信息出现IOException异常:" + e.getMessage());
        } finally {
            if (outputStream != null)
                outputStream.close();
        }
    }

}
