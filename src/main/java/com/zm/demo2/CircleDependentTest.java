package com.zm.demo2;

import com.zm.demo2.test1.MainConfig1;
import com.zm.demo2.test2.ServiceA;
import com.zm.demo2.test2.ServiceB;
import com.zm.demo2.test3.MainConfig3;
import com.zm.demo2.test3.Service1;
import com.zm.demo2.test3.Service2;
import com.zm.demo2.test4.MainConfig4;
import org.junit.Test;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * spring创建bean主要的几个步骤：
 * <p>
 * 步骤1：实例化bean，即调用构造器创建bean实例
 * 步骤2：填充属性，注入依赖的bean，比如通过set方式、@Autowired注解的方式注入依赖的bean
 * 步骤3：bean的初始化，比如调用init方法等。
 * 从上面3个步骤中可以看出，注入依赖的对象，有2种情况：
 * <p>
 * 通过步骤1中构造器的方式注入依赖
 * 通过步骤2注入依赖
 */
public class CircleDependentTest {

    /**
     * 构造器的方式注入相互依赖的bean：实例化ServiceA时，需要serviceB，而实例化ServiceB时需要servi
     * 构造器循环依赖无法解决! 创建下面2个对象，无法创建成功！会报错
     */
    @Test
    public void test1() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MainConfig1.class);
    }

    /**
     * 非构造器的方式注入相互依赖的bean：（单例bean）需要用到3级缓存
     * 由于单例bean在spring容器中只存在一个，所以spring容器中肯定是有一个缓存来存放所有已创建好的单例bean；
     * 获取单例bean之前，可以先去缓存中找，找到了直接返回，找不到的情况下再去创建，创建完毕之后再将其丢到缓存中。
     */
    @Test
    public void test2() {
        DefaultListableBeanFactory factory = new DefaultListableBeanFactory();

        factory.registerBeanDefinition("serviceA",
                BeanDefinitionBuilder.
                        genericBeanDefinition(ServiceA.class).
                        setAutowireMode(AutowireCapableBeanFactory.AUTOWIRE_BY_TYPE).
                        getBeanDefinition());
        factory.registerBeanDefinition("serviceB",
                BeanDefinitionBuilder.
                        genericBeanDefinition(ServiceB.class).
                        setScope(ConfigurableBeanFactory.SCOPE_PROTOTYPE).
                        setAutowireMode(AutowireCapableBeanFactory.AUTOWIRE_BY_TYPE).
                        getBeanDefinition());

        //System.out.println(factory.getBean("serviceB"));
        System.out.println(factory.getBean("serviceA"));
    }

    // 会报异常：位置在AbstractAutowireCapableBeanFactory.java:624
    // 主要用来判断当有循环依赖的情况下，早期暴露给别人的bean和最终的bean不一样的情况下，是否会抛出一个异常。
    @Test
    public void test3() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(MainConfig3.class);
        context.refresh();
    }

    @Test
    public void test4() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        //创建一个BeanFactoryPostProcessor：BeanFactory后置处理器
        //干预BeanFactory的创建过程，可以用来修改BeanFactory中的一些配置
        context.addBeanFactoryPostProcessor(beanFactory -> {
            if (beanFactory instanceof DefaultListableBeanFactory) {
                //将allowRawInjectionDespiteWrapping设置为true
                //allowRawInjectionDespiteWrapping这个参数用来控制是否允许循环依赖的情况下，早期暴露给被人使用的bean在后期是否可以被包装，
                // 通俗理解：是否允许早期给别人使用的bean和最终bean不一致? 这个值默认是false，表示不允许
                ((DefaultListableBeanFactory) beanFactory).setAllowRawInjectionDespiteWrapping(true);
            }
        });
        context.register(MainConfig3.class);
        context.refresh();

        System.out.println("容器初始化完毕");

        //获取service1
        Service1 service1 = context.getBean(Service1.class);
        //获取service2
        Service2 service2 = context.getBean(Service2.class);

        System.out.println("----A-----");
        service2.m1(); //@1
        System.out.println("----B-----");
        service1.m1(); //@2
        System.out.println("----C-----");
        //service2.m1方法中调用了service1.m1,这个里面拦截器没有起效啊，但是单独调用service1.m1方法，却起效了，
        // 说明service2中注入的service1不是代理对象，所以没有加上拦截器的功能，那是因为service2中注入的是早期的service1
        System.out.println(service2.getService1() == service1);
    }

    @Test
    public void test5() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MainConfig4.class);
        System.out.println("容器初始化完毕");

        //获取service1
        com.zm.demo2.test4.Service1 service1 = context.getBean(com.zm.demo2.test4.Service1.class);
        //获取service2
        com.zm.demo2.test4.Service2 service2 = context.getBean(com.zm.demo2.test4.Service2.class);

        System.out.println("----A-----");
        service2.m1(); //@1
        System.out.println("----B-----");
        service1.m1(); //@2
        System.out.println("----C-----");
        //最终service1是一个代理对象，那么你提前暴露出去的时候，注入到service2时，也必须是个代理对象
        System.out.println(service2.getService1() == service1);
    }
    /**
     * 暴露早期bean的源码：addSingletonFactory(beanName, () -> getEarlyBeanReference(beanName, mbd, bean));
     * 从3级缓存中获取bean时，会调用上面方法获取bean，容器中是否有SmartInstantiationAwareBeanPostProcessor处理器，
     * 然后会依次调用这种处理器中的getEarlyBeanReference方法，那可以自定义一个SmartInstantiationAwareBeanPostProcessor，
     * 然后在其getEarlyBeanReference中来创建代理，可确保暴露早期bean为一个代理，跟实际暴露的一致。
     */
}
