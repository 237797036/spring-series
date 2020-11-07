package com.aop.demo6;

import org.junit.Test;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.lang.Nullable;

import java.lang.reflect.Method;

public class AopTest6 {
    @Test
    public void test1() {
        Service target = new Service();

        ProxyFactory proxyFactory = new ProxyFactory();
        //设置需要被代理的对象
        proxyFactory.setTarget(target);
        //设置需要代理的接口
        proxyFactory.addInterface(IService.class);

        //强制使用cglib代理
//        proxyFactory.setProxyTargetClass(true);

        proxyFactory.addAdvice(new MethodBeforeAdvice() {
            @Override
            public void before(Method method, Object[] args, @Nullable Object target) throws Throwable {
                System.out.println(method);
            }
        });

        IService proxy = (IService) proxyFactory.getProxy();
        System.out.println("代理对象的类型：" + proxy.getClass());
        System.out.println("代理对象的父类：" + proxy.getClass().getSuperclass());
        System.out.println("代理对象实现的接口列表");
        for (Class<?> cf : proxy.getClass().getInterfaces()) {
            System.out.println(cf);
        }
        //调用代理的方法
        System.out.println("\n调用代理的方法");
        proxy.say("spring aop");
    }

    @Test
    public void test2() {
        Service target = new Service();

        ProxyFactory proxyFactory = new ProxyFactory();
        //强制使用cglib代理
        proxyFactory.setProxyTargetClass(true);
        //设置需要被代理的对象
        proxyFactory.setTarget(target);
        //设置需要代理的接口
        //有接口的情况默认会通过jdk动态代理的方式生成代理
        proxyFactory.addInterface(IService.class);

        proxyFactory.addAdvice(new MethodBeforeAdvice() {
            @Override
            public void before(Method method, Object[] args, @Nullable Object target) throws Throwable {
                System.out.println(method);
            }
        });

        IService proxy = (IService) proxyFactory.getProxy();
        System.out.println("代理对象的类型：" + proxy.getClass());
        System.out.println("代理对象的父类：" + proxy.getClass().getSuperclass());
        System.out.println("代理对象实现的接口列表");
        for (Class<?> cf : proxy.getClass().getInterfaces()) {
            System.out.println(cf);
        }
        //调用代理的方法
        System.out.println("\n调用代理的方法");
        proxy.say("spring aop");
    }
}
