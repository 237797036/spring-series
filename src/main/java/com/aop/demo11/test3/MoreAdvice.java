package com.aop.demo11.test3;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.AfterReturningAdvice;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.aop.ThrowsAdvice;
import org.springframework.lang.Nullable;

import java.lang.reflect.Method;

public class MoreAdvice {

    public static class Service3 {
        public String say(String name) {
            int i = 9/0;
            return "你好：" + name;
        }
    }

    public static class MyMethodInterceptor implements MethodInterceptor {
        @Override
        public Object invoke(MethodInvocation invocation) throws Throwable {
            System.out.println("我是MethodInterceptor start");
            //调用invocation.proceed()执行下一个拦截器
            Object result = invocation.proceed();
            System.out.println("我是MethodInterceptor end");
            //返回结果
            return result;
        }
    }

    public static class MyMethodBeforeAdvice implements MethodBeforeAdvice {

        @Override
        public void before(Method method, Object[] args, @Nullable Object target) throws Throwable {
            System.out.println("我是MethodBeforeAdvice");
        }
    }

    public static class MyAfterReturningAdvice implements AfterReturningAdvice {

        @Override
        public void afterReturning(@Nullable Object returnValue, Method method, Object[] args, @Nullable Object target) throws Throwable {
            System.out.println("我是AfterReturningAdvice");
        }
    }

    public static class MyThrowsAdvice implements ThrowsAdvice {
        public void afterThrowing(Method method, Object[] args, Object target, Exception ex) {
            System.out.println("我是ThrowsAdvice");
        }
    }
}
