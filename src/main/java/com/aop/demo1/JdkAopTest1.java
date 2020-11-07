package com.aop.demo1;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class JdkAopTest1 {
    interface IService1 {
        void m1();
    }

    interface IService2 {
        void m2();
    }

    public static class Service implements IService1, IService2 {
        @Override
        public void m1() {
            System.out.println("我是m1");
        }

        @Override
        public void m2() {
            System.out.println("我是m2");
        }
    }

    public static class CostTimeInvocationHandler implements InvocationHandler {

        private Object target;

        public CostTimeInvocationHandler(Object target) {
            this.target = target;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            long starttime = System.nanoTime();
            Object result = method.invoke(this.target, args); //将请求转发给target去处理
            System.out.println(method + "，耗时(纳秒):" + (System.nanoTime() - starttime));
            return result;
        }
    }

public static void main(String[] args) {
    Service target = new Service();
    CostTimeInvocationHandler costTimeInvocationHandler = new CostTimeInvocationHandler(target);
    //创建代理对象
    Object proxyObject = Proxy.newProxyInstance(
            target.getClass().getClassLoader(),
            new Class[]{IService1.class, IService2.class}, //创建的代理对象实现了2个接口
            costTimeInvocationHandler);
    //判断代理对象是否是Service类型的，肯定是false咯
    System.out.println(String.format("proxyObject instanceof Service = %s", proxyObject instanceof Service));
    //判断代理对象是否是IService1类型的，肯定是true
    System.out.println(String.format("proxyObject instanceof IService1 = %s", proxyObject instanceof IService1));
    //判断代理对象是否是IService2类型的，肯定是true
    System.out.println(String.format("proxyObject instanceof IService2 = %s", proxyObject instanceof IService2));

    //将代理转换为IService1类型
    IService1 service1 = (IService1) proxyObject;
    //调用IService2的m1方法
    service1.m1();
    //将代理转换为IService2类型
    IService2 service2 = (IService2) proxyObject;
    //调用IService2的m2方法
    service2.m2();
    //输出代理的类型
    System.out.println("代理对象的类型:" + proxyObject.getClass());
}
}
