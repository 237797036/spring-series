package com.zj.demo16;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 统计'所有接口'方法耗时的通用代理
 * jdk动态代理只能为接口创建代理,不能对普通的类进行代理（因为所有生成的代理类的父类为Proxy，Java类继承机制不允许多重继承）
 * java动态代理使用Java原生的反射API进行操作，在生成类上比较高效；CGLIB使用ASM框架直接对字节码进行操作，在类的执行过程中比较高效
 * 特点：
 * 只能为接口创建代理，且创建出来的代理都是java.lang.reflect.Proxy的子类
 * 当调用代理对象任意方法时，会被InvocationHandler接口中的invoke方法处理，这个接口内容很关键
 */
//代理处理器
public class CostTimeInvocationHandler implements InvocationHandler {

    private Object target;

    public CostTimeInvocationHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        long starTime = System.nanoTime();
        Object result = method.invoke(target, args);//调用目标方法
        long endTime = System.nanoTime();
        System.out.println(target.getClass() + "." + method.getName() + "()方法耗时(纳秒):" + (endTime - starTime));
        return result;
    }

    /**
     * 用来创建targetInterface接口的代理对象
     * 返回一个代理对象，当调用代理对象的任何方法时，会被InvocationHandler接口的invoke方法处理
     *
     * @param target          需要被代理的对象（目标对象，需要实现targetInterface接口）
     * @param targetInterface 被代理的接口（需要创建代理的接口）
     * @return
     */
    public static <T> T createProxy(Object target, Class<T> targetInterface) {
        if (!targetInterface.isInterface()) {
            throw new IllegalStateException("targetInterface必须是接口类型!");
        } else if (!targetInterface.isAssignableFrom(target.getClass())) {
            throw new IllegalStateException("target必须是targetInterface接口的实现类!");
        }
        return (T) Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), new CostTimeInvocationHandler(target));
    }
}
