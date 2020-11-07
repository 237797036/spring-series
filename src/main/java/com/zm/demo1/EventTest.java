package com.zm.demo1;

import com.zm.demo1.test0.userregister.MainConfig0;
import com.zm.demo1.test1.OrderCreateEvent;
import com.zm.demo1.test1.SendEmailOnOrderCreateListener;
import com.zm.demo1.test2.MainConfig2;
import com.zm.demo1.test3.MainConfig3;
import com.zm.demo1.test4.MainConfig4;
import com.zm.demo1.test5.MainConfig5;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.context.event.SimpleApplicationEventMulticaster;

import java.util.concurrent.TimeUnit;

/**
 * spring中事件是使用接口的方式还是使用注解的方式？具体使用哪种方式都可以，不过在公司内部最好大家都统一使用一种方式
 * 异步事件的模式，通常将一些非主要的业务放在监听器中执行，因为监听器中存在失败的风险，所以使用的时候需要注意。
 * 如果只是为了解耦，但是被解耦的次要业务也是必须要成功的，可以使用消息中间件的方式来解决这些问题。
 */
public class EventTest {

    //用到的和事件相关的几个类，都是我们自己实现的，其实spring中已经帮我们实现好了，用起来更容易一些
    @Test
    public void test0() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MainConfig0.class);
        //获取用户注册服务
        com.zm.demo1.test0.userregister.UserRegisterService userRegisterService = context.getBean(com.zm.demo1.test0.userregister.UserRegisterService.class);
        //模拟用户注册
        userRegisterService.registerUser("路人甲Java");
    }

    // 硬编码的方式使用spring事件

    /**
     * 步骤1：定义事件，需要继承ApplicationEvent类
     * 步骤2：定义监听器，需要实现ApplicationListener接口，方法onApplicationEvent用来处理感兴趣的事件
     * 步骤3：创建事件广播器，可实现ApplicationEventMulticaster接口，也可使用系统提供的SimpleApplicationEventMulticaster
     * 步骤4：向广播器中注册事件监听器，将事件监听器注册到广播器ApplicationEventMulticaster中
     * 步骤5：通过广播器发布事件，调用ApplicationEventMulticaster#multicastEvent方法广播事件。此时广播器中对这个事件感兴趣的监听器会处理这个事件
     */

    @Test
    public void test1() throws InterruptedException {
        //创建事件广播器
        ApplicationEventMulticaster applicationEventMulticaster = new SimpleApplicationEventMulticaster();
        //注册事件监听器（手动）
        applicationEventMulticaster.addApplicationListener(new SendEmailOnOrderCreateListener());
        //广播事件订单创建事件
        applicationEventMulticaster.multicastEvent(new OrderCreateEvent(applicationEventMulticaster, 1L));
    }

    //上面只是演示了spring中事件的使用，平时我们使用spring的时候就这么使用？
    //非也非也，上面只是我给大家演示了一下原理。

    /**
     * 1.AnnotationConfigApplicationContext和ClassPathXmlApplicationContext都继承了AbstractApplicationContext
     * 2.AbstractApplicationContext实现了ApplicationEventPublisher接口
     * 3.AbstractApplicationContext内部有个ApplicationEventMulticaster类型的字段
     *
     * 第三条：AbstractApplicationContext容器已集成了事件广播器ApplicationEventMulticaster，
     * 说明AbstractApplicationContext内部有具体事件相关功能的，这些功能通过其内部的ApplicationEventMulticaster实现的，
     * 也就是说将事件的功能委托给了内部的ApplicationEventMulticaster来实现。
     *
     * 第二条：spring中不是有个ApplicationEventMulticaster接口么，此处怎么又来了一个发布事件ApplicationEventPublisher接口？
     * AbstractApplicationContext中的publishEvent方法会委托ApplicationEventMulticaster#multicastEvent进行发布事件。
     */

    /**
     * 获取ApplicationEventPublisher对象
     * 如果我们想在普通的bean中获取ApplicationEventPublisher对象，需要实现ApplicationEventPublisherAware接口
     * <p>
     * public interface ApplicationEventPublisherAware extends Aware {
     * void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher);
     * }
     * spring容器会自动通过setApplicationEventPublisher方法将ApplicationEventPublisher注入进来，此时就可以使用这个来发布事件。
     */

    /**
     * 通过接口的方式创建一个监听器：
     * 原理：在bean实例化后置阶段
     *
     * spring容器在创建bean过程中，会判断bean是否为ApplicationListener类型，
     * 进而会将其作为事件监听器注册到AbstractApplicationContext#applicationEventMulticaster中
     * 源码：org.springframework.context.support.ApplicationListenerDetector#postProcessAfterInitialization
     */
    @Test
    public void test2() throws InterruptedException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(MainConfig2.class);
        context.refresh();
        //获取用户注册服务
        com.zm.demo1.test2.UserRegisterService userRegisterService = context.getBean(com.zm.demo1.test2.UserRegisterService.class);
        //模拟用户注册
        userRegisterService.registerUser("路人甲Java");
    }

    /**
     * 通过@EventListener注解方式来创建一个监听器：
     * 原理：在所有单例bean实例化完成后阶段
     *
     * 调用SmartInitializingSingleton#afterSingletonsInstantiated方法
     * spring中处理@EventListener注解源码位于：
     * org.springframework.context.event.EventListenerMethodProcessor#afterSingletonsInstantiated
     *
     * EventListenerMethodProcessor实现了SmartInitializingSingleton接口，
     * SmartInitializingSingleton接口中的afterSingletonsInstantiated方法会在所有单例的bean创建完成之后被spring容器调用
     */
    @Test
    public void test3() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(MainConfig3.class);
        context.refresh();
        //获取用户注册服务
        com.zm.demo1.test3.UserRegisterService userRegisterService = context.getBean(com.zm.demo1.test3.UserRegisterService.class);
        //模拟用户注册
        userRegisterService.registerUser("路人甲Java");
    }

    /**
     * 监听器支持排序功能
     *
     * 通过接口实现监听器的情况
     * 如果自定义的监听器是通过ApplicationListener接口实现的，那么指定监听器的顺序有三种方式
     *
     * 方式1：实现org.springframework.core.Ordered接口
     * 需要实现一个getOrder方法，返回顺序值，值越小，顺序越高
     *
     * 方式2：实现org.springframework.core.PriorityOrdered接口
     * PriorityOrdered接口继承了方式一中的Ordered接口，所以如果实现PriorityOrdered接口，也需要实现getOrder方法。
     *
     * 方式3：类上使用@org.springframework.core.annotation.Order注解
     *
     *
     * 这几种方式排序规则：
     * PriorityOrdered#getOrder ASC,Ordered或@Order ASC
     *
     *
     * 通过@EventListener实现事件监听器的情况
     * 可以在标注@EventListener的方法上面使用@Order(顺序值)注解来标注顺序
     */

    @Test
    public void test4() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(MainConfig4.class);
        context.refresh();
        //获取用户注册服务
        com.zm.demo1.test4.UserRegisterService userRegisterService = context.getBean(com.zm.demo1.test4.UserRegisterService.class);
        //模拟用户注册
        userRegisterService.registerUser("路人甲Java");
    }
    //从输出可以看出上面程序的执行都在主线程中完成的，说明监听器中的逻辑和注册逻辑在一个线程中执行的，
    // 如果监听器的逻辑比较耗时或者失败，会直接会导致注册失败，通常将一些非主要逻辑可以放在监听器中执行，
    // 至于这些非主要逻辑成功或者失败，最好不要对主逻辑产生影响，所以最好能将监听器的运行和主业务隔离开，放在不同的线程中执行，
    // 主业务不用关注监听器的结果，spring中支持这种功能，下面继续看。

    // SimpleApplicationEventMulticaster，这个类支持监听器异步调用
    // 在SimpleApplicationEventMulticaster中调用事件监听器
    @Test
    public void test5() throws InterruptedException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(MainConfig5.class);
        context.refresh();
        //获取用户注册服务
        com.zm.demo1.test5.UserRegisterService userRegisterService = context.getBean(com.zm.demo1.test5.UserRegisterService.class);
        //模拟用户注册
        userRegisterService.registerUser("路人甲Java");
        TimeUnit.SECONDS.sleep(3);
        System.out.println();
        //getBeanDefinitionNames用于获取容器中所有bean的名称
        for (String beanName : context.getBeanDefinitionNames()) {
            System.out.println(beanName + ":" + context.getBean(beanName));
        }
    }

}
