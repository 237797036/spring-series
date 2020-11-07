package com.aop.demo9.test2;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class AspectTest2 {

    //  匹配的类型是C1，即被代理的对象的类型必须是C1类型才行，需要和C1完全匹配。所以测试用例中不会拦截
//    @Pointcut("within(C1)")
    // 将within表达式修改为下面任意一种才可以匹配
//     @Pointcut("within(C1+)")
    @Pointcut("within(C2)") //继承下来的C1不会拦截
    public void pc() {
    }

    @Before("pc()")
    public void beforeAdvice(JoinPoint joinpoint) {
        System.out.println(joinpoint);
    }

}
