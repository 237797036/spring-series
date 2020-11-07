package com.aop.demo11.test1;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component //@1 使用 @Component 将这个类注册到spring容器
@Aspect //@2 使用 @Aspect 标注这是一个 AspectJ @Aspect
public class Aspect1 {

    // 定义切入点 拦截test1包及其子包中所有类的所有方法
    @Pointcut("execution(* com.aop.demo11.test1..*(..))") //@3
    public void pc() {
    }

    @Before("com.aop.demo11.test1.Aspect1.pc()") //@4
    public void before(JoinPoint joinPoint) {
        System.out.println("我是前置通知,target:" + joinPoint.getTarget()); //@5
    }
}
