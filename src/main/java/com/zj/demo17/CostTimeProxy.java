package com.zj.demo17;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * 实现通用的统计'任意类'方法耗时代理类
 * <p>
 * cglib是一个强大、高性能的字节码生成库，它用于在运行时扩展Java类和实现接口；
 * 本质上是动态的生成一个子类去覆盖所要代理的类（非final修饰的类和方法）。
 * Enhancer可能是CGLIB中最常用的一个类，和jdk中的Proxy不同的是，Enhancer既能够代理普通的class，也能够代理接口。
 * Enhancer创建一个被代理对象的子类，并且拦截所有的方法调用（包括从Object中继承的toString和hashCode方法）
 * Enhancer不能够拦截final方法，例如Object.getClass()方法，由Java final方法语义决定。
 * Enhancer也不能对final类进行代理操作。
 */
public class CostTimeProxy implements MethodInterceptor {
    //目标对象
    private Object target;

    public CostTimeProxy(Object target) {
        this.target = target;
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        long starTime = System.nanoTime();
        //调用被代理对象（即target）的方法，获取结果
        Object result = method.invoke(target, objects); //@1
        long endTime = System.nanoTime();
        System.out.println(method + "，耗时(纳秒)：" + (endTime - starTime));
        return result;
    }

    /**
     * 创建任意类的代理对象
     *
     * @param target
     * @param <T>
     * @return
     */
    public static <T> T createProxy(T target) {
        CostTimeProxy costTimeProxy = new CostTimeProxy(target);
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(target.getClass());
        enhancer.setCallback(costTimeProxy);
        return (T) enhancer.create();
    }
}
