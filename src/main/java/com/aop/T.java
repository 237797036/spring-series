package com.aop;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * @Author zhongJun
 * @Date 2020-09-17 22:16
 */
public class T {
    public static class TracingInterceptor implements MethodInterceptor {
        @Override
        public Object invoke(MethodInvocation i) throws Throwable {
            System.out.println("method " + i.getMethod() + " is called on " + i.getThis() + " with args " + i.getArguments());
            Object ret = i.proceed();//转到拦截器链中的下一个拦截器
            System.out.println("method " + i.getMethod() + " returns " + ret);
            return ret;
        }
    }
}
