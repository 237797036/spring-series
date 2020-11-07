package com.aop.demo11;

import com.aop.demo11.test1.CarService;
import com.aop.demo11.test1.MainConfig1;
import com.aop.demo11.test1.UserService;
import com.aop.demo11.test2.MainConfig2;
import com.aop.demo11.test2.Service2;
import com.aop.demo11.test3.MoreAdvice;
import com.aop.demo11.test4.MainConfig4;
import com.aop.demo11.test4.Service4;
import org.junit.Test;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AbstractAspectJAdvice;
import org.springframework.aop.aspectj.InstantiationModelAwarePointcutAdvisor;
import org.springframework.aop.framework.Advised;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class AspectTest11 {

    // @EnableAspectJAutoProxy自动为bean创建代理对象
    @Test
    public void test1() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(MainConfig1.class);
        context.refresh();
        UserService userService = context.getBean(UserService.class);
        userService.say();
        CarService carService = context.getBean(CarService.class);
        carService.say();
    }

    // @EnableAspectJAutoProxy 允许spring容器中通过Advisor 、@Aspect 来定义通知，
    // 当spring容器中存在多个Advisor、@Aspect时，组成的拦截器调用链顺序是什么样的呢？
    @Test
    public void test2() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(MainConfig2.class);
        context.refresh();
        Service2 service2 = context.getBean(Service2.class);
        System.out.println(service2.say("路人"));
    }

    @Test
    public void test5() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(MainConfig2.class);
        context.refresh();
        Service2 service2 = context.getBean(Service2.class);
        if (service2 instanceof Advised) {
            Advisor[] advisors = ((Advised) service2).getAdvisors();
            for (Advisor advisor : advisors) {
                if (advisor instanceof InstantiationModelAwarePointcutAdvisor) {
                    InstantiationModelAwarePointcutAdvisor advisor1 = (InstantiationModelAwarePointcutAdvisor) advisor;
                    AbstractAspectJAdvice advice = (AbstractAspectJAdvice) advisor1.getAdvice();
                    System.out.println(advice.getAspectJAdviceMethod());
                } else {
                    System.out.println(advisor.getAdvice());
                }
            }
        }
    }

    @Test
    public void test3() {
        //创建目标对象
        MoreAdvice.Service3 target = new MoreAdvice.Service3();
        //创建代理工厂，通过代理工厂来创建代理对象
        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.setTarget(target);
        //依次为目标对象添加4种通知
        proxyFactory.addAdvice(new MoreAdvice.MyMethodInterceptor());
        proxyFactory.addAdvice(new MoreAdvice.MyMethodBeforeAdvice());
        proxyFactory.addAdvice(new MoreAdvice.MyAfterReturningAdvice());
        proxyFactory.addAdvice(new MoreAdvice.MyThrowsAdvice());
        //获取到代理对象
        MoreAdvice.Service3 proxy = (MoreAdvice.Service3) proxyFactory.getProxy();
        //通过代理对象访问目标方法say
        System.out.println(proxy.say("路人"));
    }

    @Test
    public void test4() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(MainConfig4.class);
        context.refresh();
        Service4 service4 = context.getBean(Service4.class);
        System.out.println(service4.say("路人"));
    }

}


