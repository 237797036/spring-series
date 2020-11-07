package com.aop.demo9.test8;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class AspectTest8 {
    @Pointcut("@args(..,com.aop.demo9.test8.Ann8)") //匹配参数数量大于等于1，且最后一个参数所属的类型上有Ann8注解
    public void pc() {
    }

    @Before("pc()")
    public void beforeAdvice(JoinPoint joinPoint) {
        System.out.println(joinPoint);
    }
}
