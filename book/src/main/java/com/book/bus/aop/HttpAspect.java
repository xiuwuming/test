package com.book.bus.aop;


import com.book.bus.utils.IpUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 通过aop拦截请求 记录运行信息
 */
@Aspect
@Component
public class HttpAspect {
    private static long startTime;
    private static long endTime;
    private static final Logger log = LoggerFactory.getLogger(HttpAspect.class);

    /**
     * 切点表达式
     */
    @Pointcut("execution(public * com.book.bus.controller..*.*(..))")

    /**
     * 切点签名
     */
    public void print() {

    }

    /**
     * @Before注解表示在具体的方法之前执行
     * @param joinPoint
     */
    @Before("print()")
    public void before(JoinPoint joinPoint) {
        startTime = System.currentTimeMillis();
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        String requestURI = request.getRequestURI();
        String remoteAddr = IpUtil.getIpAddr(request);
        String requestMethod = request.getMethod();
        String declaringTypeNamepeName = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        log.info("请求url:=" + requestURI + ",客户端ip:=" + remoteAddr + ",请求方式:=" + requestMethod + ",请求的类名:=" + declaringTypeNamepeName + ",方法名:=" + methodName);
    }


    /**
     * @After注解表示在方法执行之后执行
     */
    @After("print()")
    public void after() {
        endTime = System.currentTimeMillis() - startTime;
    }


    /**
     * @AfterReturning注解用于获取方法的返回值
     * @param object
     */
    @AfterReturning(pointcut = "print()", returning = "object")
    public void getAfterReturn(Object object) {
        log.info("本次接口耗时={}ms", endTime);
    }
}
