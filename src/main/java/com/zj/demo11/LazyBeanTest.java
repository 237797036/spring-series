package com.zj.demo11;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * bean默认是实时初始化的，可以通过bean元素的lazy-init="true"将bean定义为延迟初始化
 * <p>
 * 一、实时初始化bean
 * a 优点：
 * 1 更早发现bean定义的错误：实时初始化的bean如果定义有问题，会在容器启动过程中会抛出异常，让开发者快速发现问题
 * 2 查找bean更快：容器启动完毕，bean已经完全创建好了，并被缓存在spring容器中，当使用时容器直接返回，速度非常快
 * b 缺点：
 * 如果程序中定义的bean非常多，并且有些bean创建过程比较耗时，会导致系统消耗的资源多，会让整个系统启动时间长
 * c 解决方案：bean延迟初始化
 * <p>
 * 二、延迟初始化
 * 和实时初始化刚好相反，bean在容器 启动过程中 不会创建，而是在使用时才会创建。
 * bean什么时候会被使用？
 * 1 被其他bean作为依赖进行注入时，比如通过property元素的ref属性引用、构造器注入、set注入、自动注入，都会导致被依赖bean创建
 * 2 开发者自己写代码向容器中查找bean时，如调用容器的getBean方法获取bean。
 * 以上：会导致延迟初始化的bean 被创建
 */
public class LazyBeanTest {

    @Test
    public void actualTimeBean() {
        System.out.println("spring容器启动中...");
        String beanXml = "classpath:/com/zj/demo11/actualTimeBean.xml";
        new ClassPathXmlApplicationContext(beanXml); //启动spring容器
        System.out.println("spring容器启动完毕...");
    }

    @Test
    public void lazyInitBean() {
        System.out.println("spring容器启动中...");
        String beanXml = "classpath:/com/zj/demo11/lazyInitBean.xml";
        ApplicationContext context = new ClassPathXmlApplicationContext(beanXml); //启动spring容器
        System.out.println("spring容器启动完毕...");
        System.out.println("从容器中开始查找LazyInitBean");
        LazyInitBean lazyInitBean = context.getBean(LazyInitBean.class);
        System.out.println("LazyInitBean:" + lazyInitBean);
    }

    /**
     * 实时初始化的bean，这个bean在创建过程中需要用到lazyInitBean，此时容器会去查找lazyInitBean这个bean，然后会进行初始化，
     * 所以这2个bean都在容器启动过程中被创建。
     */
    @Test
    public void actualTimeDependencyLazyBean() {
        System.out.println("spring容器启动中...");
        String beanXml = "classpath:/com/zj/demo11/actualTimeDependencyLazyBean.xml";
        ApplicationContext context = new ClassPathXmlApplicationContext(beanXml); //启动spring容器
        System.out.println("spring容器启动完毕...");
    }
}
