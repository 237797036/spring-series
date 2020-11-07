package com.aop.demo10.test4;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

@Aspect
public class AfterAspect4 {

    @Pointcut("execution(* com.aop.demo10.test1.Service1.*(..))")
    public void pc() {
    }

    @After("com.aop.demo10.test4.AfterAspect4.pc()")
    public void after(JoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        System.out.println(String.format("%s,执行完毕!", methodSignature.getMethod()));
    }
}
