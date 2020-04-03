package com.framework.common;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
public class ApiLogAspect {

    private static Logger logger = LoggerFactory.getLogger(ApiLogAspect.class);

    /**
     * 定义一个切点（需要横切的位置）
     * 第一个*表示方法返回值的类型任意.
     * 第二个中间的一连串包名表示路径.包名后面的表示..表示当前包及子包.
     * 第二个星号表示的是所有的类.
     * .*(**)表示的是任何方法名,括号里面表示参数,两个点表示的是任意参数类型.
     */
    @Pointcut("execution(* com.softmax.marvel.controller..*.*(..))")
    public void apiRequestLog() {

    }

    /**
     * 定义前置通知（需要在切点方法的前面需要执行的动作处理）
     */
    @Before("apiRequestLog()")
    public void doBefore(JoinPoint joinPoint) {
        logger.info("前置通知日志输出");
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        // 获取url
        logger.info("url={}", request.getRequestURL());
        //获取请求method
        logger.info("method={}", request.getMethod());
        //获取请求ip
        logger.info("ip={}", request.getRemoteAddr());
        // 获取处理请求的类方法
        logger.info("class_method={}", joinPoint.getSignature().getDeclaringType() + "." + joinPoint.getSignature().getName());
        // 获取请求方法传入的参数
        logger.info("args={}", joinPoint.getArgs());
    }

    @After("apiRequestLog()")
    public void doAfter() {
        logger.info("后置通知日志输出");
    }

    @AfterReturning(returning = "object", pointcut = "apiRequestLog()")
    public void doReturning(Object object) {
        logger.info("response={}", object.toString());
    }

}
