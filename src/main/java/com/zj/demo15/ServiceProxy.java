package com.zj.demo15;

/**
 * 一、比较好的方式：
 * 为IService接口创建一个代理类，通过这个代理类来间接访问IService接口的实现类，
 * 在这个代理类中去做耗时及发送至监控的代码。
 * <p>
 * 假如需要给系统中所有接口都加上统计耗时的功能，就需要给每个接口创建一个代理类！
 * 此时代码量和测试的工作量也是巨大的，那么能不能写一个通用的代理类，来满足上面的功能呢？
 * <p>
 * 二、通用代理的2种实现：1. jdk动态代理 2. cglib代理
 * <p>
 * jdk自带的代理使用上有个限制：只能为接口创建代理类，如果需要给具体的类创建代理类，需要用cglib
 */

// IService的代理类
public class ServiceProxy implements IService {
    //目标对象，被代理的对象
    private IService target;

    public ServiceProxy(IService target) {
        this.target = target;
    }

    @Override
    public void m1() {
        long starTime = System.nanoTime();
        target.m1();
        long endTime = System.nanoTime();
        System.out.println(this.target.getClass() + ".m1()方法耗时(纳秒):" + (endTime - starTime));
    }

    @Override
    public void m2() {
        long starTime = System.nanoTime();
        target.m2();
        long endTime = System.nanoTime();
        System.out.println(this.target.getClass() + ".m2()方法耗时(纳秒):" + (endTime - starTime));
    }

    @Override
    public void m3() {
        long starTime = System.nanoTime();
        target.m3();
        long endTime = System.nanoTime();
        System.out.println(this.target.getClass() + ".m3()方法耗时(纳秒):" + (endTime - starTime));
    }
}
