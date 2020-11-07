package com.aop.demo7;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.junit.Test;
import org.springframework.aop.framework.ProxyFactory;

class Service {
    public void m1() {
        System.out.println("m1");
        m2();
//        ((Service) AopContext.currentProxy()).m2();
    }

    public void m2() {
        System.out.println("m2");
    }
}

// 为什么没有输出m2方法的耗时?
//
//原因：m2方法是在m1方法中通过this的方式来调用的，this实际上指向的是上面代码中的target对象。
//
//如何能让此处的m2也能被增强，需要通过代理来调用m2方法才可，
// 可以将代理对象暴露在threadLocal中，然后在m1方法中获取到threadLocal中的代理对象，通过代理对象来调用m2就可以了。
public class AopTest7 {

    /**
     * 将代理暴露在threadLocal中
     */
    @Test
    public void test1() {
        Service target = new Service();

        ProxyFactory proxyFactory = new ProxyFactory();
        // 这个功能还是挺有用的，以后我估计大家是可以用到的。
        // proxyFactory.setExposeProxy(true); // 配置代理创建时，将其暴露出去
        proxyFactory.setTarget(target);

        proxyFactory.addAdvice(new MethodInterceptor() {
            @Override
            public Object invoke(MethodInvocation invocation) throws Throwable {
                long startTime = System.nanoTime();
                Object result = invocation.proceed();
                long endTime = System.nanoTime();
                System.out.println(String.format("%s方法耗时(纳秒):%s", invocation.getMethod().getName(), endTime - startTime));
                return result;
            }
        });

        Service proxy = (Service) proxyFactory.getProxy();
        proxy.m1();
    }
}
