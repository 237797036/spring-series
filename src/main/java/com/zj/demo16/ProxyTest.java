package com.zj.demo16;

import org.junit.Test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxyTest {

    /**
     * 1.调用Proxy.getProxyClass方法获取代理类的Class对象
     * 2.使用InvocationHandler接口创建代理类的处理器
     * 3.通过代理类和InvocationHandler创建代理对象
     * 4.上面已经创建好代理对象，接着就可以使用代理对象
     *
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws InstantiationException
     */
    @Test
    public void m1() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        // 1. 获取接口对应的代理类
        Class<IService> proxyClass = (Class<IService>) Proxy.getProxyClass(IService.class.getClassLoader(), IService.class);
        // 2. 创建代理类的处理器
        InvocationHandler invocationHandler = new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                System.out.println("我是InvocationHandler，被调用的方法是：" + method.getName());
                return null;
            }
        };
        // 3. 创建代理实例
        IService proxy = proxyClass.getConstructor(InvocationHandler.class).newInstance(invocationHandler);
        // 4. 调用代理的方法
        proxy.m1();
        proxy.m2();
        proxy.m3();
    }

    /**
     * 1.使用InvocationHandler接口创建代理类的处理器
     * 2.使用Proxy类的静态方法newProxyInstance直接创建代理对象
     * 3.使用代理对象
     *
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws InstantiationException
     */
    @Test
    public void m2() {
        // 1. 创建代理类的处理器
        InvocationHandler invocationHandler = new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                System.out.println("我是InvocationHandler，被调用的方法是：" + method.getName());
                return null;
            }
        };
        // 2. 创建代理实例
        IService proxy = (IService) Proxy.newProxyInstance(IService.class.getClassLoader(), new Class[]{IService.class}, invocationHandler);
        // 3. 调用代理对象的方法
        proxy.m1();
        proxy.m2();
        proxy.m3();
    }

    @Test
    public void costTimeProxy() {
        IService proxyA = CostTimeInvocationHandler.createProxy(new ServiceA(), IService.class);
        IService proxyB = CostTimeInvocationHandler.createProxy(new ServiceB(), IService.class);
        proxyA.m1();
        proxyA.m2();
        proxyA.m3();

        proxyB.m1();
        proxyB.m2();
        proxyB.m3();
    }

    /**
     * 当创建一个新的接口时，不需要再去新建一个代理类，
     * 只需要使用CostTimeInvocationHandler.createProxy创建一个新的代理实例
     */
    @Test
    public void userService() {
        IUserService proxy = CostTimeInvocationHandler.createProxy(new UserService(), IUserService.class);
        proxy.insert("路人甲Java");
        System.out.println(proxy.getClass());

        System.out.println();

        proxy.get();

    }
}
