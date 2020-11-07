package com.aop.demo8;

import com.aop.demo8.test1.MainConfig1;
import com.aop.demo8.test1.Service1;
import com.aop.demo8.test2.MainConfig2;
import com.aop.demo8.test3.MainConfig3;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class AopTest8 {

    // ProxyFactoryBean方式，手动创建代理对象
    @Test
    public void test1() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MainConfig1.class);
        //获取代理对象，代理对象bean的名称为注册ProxyFactoryBean的名称，即：service1Proxy
        Service1 bean = context.getBean("service1Proxy", Service1.class);
        System.out.println("----------------------");
        //调用代理的方法
        bean.m1();
        System.out.println("----------------------");
        //调用代理的方法
        bean.m2();
    }

    /**
     * ProxyFactoryBean中的interceptorNames，用来指定拦截器的bean名称列表，常用的2种方式。
     * 批量的方式
     * 非批量的方式
     */

    @Test
    public void test2() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MainConfig2.class);
        //获取代理对象，代理对象bean的名称为注册ProxyFactoryBean的名称，即：service1Proxy
        Service1 bean = context.getBean("service1Proxy", Service1.class);
        System.out.println("----------------------");
        //调用代理的方法
        bean.m1();
        System.out.println("----------------------");
        //调用代理的方法
        bean.m2();
    }

@Test
public void test3() {
    AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MainConfig3.class);
    //获取代理对象，代理对象bean的名称为注册ProxyFactoryBean的名称，即：service1Proxy
    Service1 bean = context.getBean("service1Proxy", Service1.class);
    System.out.println("----------------------");
    //调用代理的方法
    bean.m1();
    System.out.println("----------------------");
    //调用代理的方法
    bean.m2();
}
}
