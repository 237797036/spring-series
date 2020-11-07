package com.zj.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 可以使用@Order注解指定切面的优先级，值越小优先级越高
 */
@Order(2)
@Component
@Aspect
public class ValidationAspect {

    /**
     * 定义一个方法，用于声明切入点表达式。一般的，该方法中再不需要添加其他的代码
     * 使用@Pointcut 来声明切入点表达式
     * 后面的其他通知直接使用方法名引用即可
     */
    @Pointcut("execution(* com.zj.service.ZjDyService.*(..))")
    public void forAspect() {

    }

    //声明该方法是一个前置通知：在目标方法开始之前执行
    @Before("forAspect()")
    public void before(JoinPoint joinpoint) {
        System.out.println("[Aspect2] before advice");
    }

    //后置通知：在目标方法执行后（无论是否发生异常），执行的通知
    //在后置通知中，还不能访问目标方法执行的结果
    @After("forAspect()")
    public void after(JoinPoint joinPoint) {
        System.out.println("[Aspect2] after advice");
    }


    /**
     * 返回通知：方法正常结束后执行的代码
     * 可以访问到方法的返回值，但不能修改
     */
    @AfterReturning(value = "forAspect()", returning = "result")
    public void afterReturning(JoinPoint joinPoint, Object result) {
        System.out.println("[Aspect2] afterReturning advice");
    }

    /**
     * 异常通知：方法出现异常时执行的代码
     * 可以访问到异常对象，可以指定在出现特定异常时在执行通知代码
     */
    @AfterThrowing(value = "forAspect()", throwing = "ex")
    public void afterThrowing(JoinPoint joinPoint, Exception ex) {
        System.out.println("[Aspect2] afterThrowing advice");
    }

    /**
     * 环绕通知需要携带ProceedingJoinPoint类型的参数
     * 环绕通知类似于动态代理的全过程：ProceedingJoinPoint类型的参数可以决定是否执行目标方法。
     * 环绕通知必须有返回值，返回值即为目标方法的返回值，也可以修改返回值。
     */
    @Around("forAspect()")
    public Object around(ProceedingJoinPoint pjd) {
        Object result = null;
        //执行目标方法
        try {
            //前置通知
            System.out.println("[Aspect2] around advice : start");
            result = pjd.proceed();//此行注释掉，业务方法不会执行，本aspect的before advice 也不会执行
            //返回通知
            System.out.println("[Aspect2] around advice normal run");
        } catch (Throwable e) {
            //异常通知
            System.out.println("[Aspect2] around advice catch exception");
            throw new RuntimeException(e.getMessage());
        }
        //后置通知
        System.out.println("[Aspect2] around advice : end");
        return result;
    }
}