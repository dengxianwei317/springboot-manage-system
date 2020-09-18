package com.spring.aspect;

import com.spring.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Aspect
@Component
@Slf4j
public class SysLogAspect {

    /**
     * service包及子包的切入点
     */
    @Pointcut("execution(public * com.spring.service..*.*(..))")
    public void serviceAspect() {
    }

    /**
     * controller包及子包的切入点
     */
    @Pointcut("execution(public * com.spring.controller..*.*(..))")
    public void controllerAspect() {
    }

    /**
     * shiro包及子包的切入点
     */
    @Pointcut("execution(public * com.spring.shiro..*.*(..))")
    public void shiroAspect() {
    }

    @Before("controllerAspect()") //在切入点的方法run之前执行
    public void doBeforeController(JoinPoint joinPoint) {
        log.info("进入Controller日志切面前置通知!!");

        String params = getParams(joinPoint);

        log.info("请求的参数信息：" + params);
    }

    @AfterReturning(returning = "obj", pointcut = "controllerAspect()")
    public void doAfterReturningController(Object obj) throws Throwable {
        // 处理完请求，返回内容
        log.info("Controller返回接口响应参数:" + JsonUtils.toJsonString(obj));
    }

    /**
     * 后置异常通知
     * 定义一个名字，该名字用于匹配通知实现方法的一个参数名，当目标方法抛出异常返回后，将把目标方法抛出的异常传给通知方法；
     * throwing:限定了只有目标方法抛出的异常与通知方法相应参数异常类型时才能执行后置异常通知，否则不执行，
     * 对于throwing对应的通知方法参数为Throwable类型将匹配任何异常。
     */
    @AfterThrowing(pointcut = "controllerAspect()", throwing = "exception")
    public void doAfterThrowingController(JoinPoint joinPoint, Throwable exception) {
        log.info("进入Controller日志切面异常通知!!");
        log.error("方法名：" + joinPoint.getSignature().getName(), exception);
    }

    @Before("serviceAspect()") //在切入点的方法run之前执行
    public void doBeforeService(JoinPoint joinPoint) {
        log.info("进入Service日志切面前置通知!!");

        String params = getParams(joinPoint);

        log.info("请求的参数信息：" + params);
    }

    /*@AfterReturning(returning = "obj", pointcut = "serviceAspect()")
    public void doAfterReturningService(Object obj) throws Throwable {
        // 处理完请求，返回内容
        log.info("Service返回接口响应参数:" + JsonUtils.toJsonString(obj));
    }*/

    @AfterThrowing(pointcut = "serviceAspect()", throwing = "exception")
    public void doAfterThrowingService(JoinPoint joinPoint, Throwable exception) {
        log.info("进入Service日志切面异常通知!!");
        log.error("方法名：" + joinPoint.getSignature().getName(), exception);
    }

    @AfterThrowing(pointcut = "shiroAspect()", throwing = "exception")
    public void doAfterThrowingShiro(JoinPoint joinPoint, Throwable exception) {
        log.info("进入Shiro日志切面异常通知!!");
        log.error("方法名：" + joinPoint.getSignature().getName(), exception);
    }

    private String getParams(JoinPoint joinPoint) {
        //通知的签名
        Signature signature = joinPoint.getSignature();
        //代理的是哪一个方法
        log.info("代理方法:" + signature.getName());
        //AOP代理类的名字
        log.info("AOP代理类:" + signature.getDeclaringTypeName());
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        //从获取RequestAttributes中获取HttpServletRequest的信息
        HttpServletRequest request = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);
        //获取Session信息
        //HttpSession session = (HttpSession) requestAttributes.resolveReference(RequestAttributes.REFERENCE_SESSION);

        //获取请求参数
        Enumeration<String> enumeration = request.getParameterNames();
        Map<String, String> parameterMap = new HashMap<>();
        while (enumeration.hasMoreElements()) {
            String parameter = enumeration.nextElement();
            parameterMap.put(parameter, request.getParameter(parameter));
        }

        String params = JsonUtils.toJsonString(parameterMap);
        return params;
    }
}
