package com.zj.demo12;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * bean定义继承案例
 * 1 没有指定class对象，但是多了一个abstract="true"的属性，表示这个bean是抽象的，
 *   abstract为true的bean在spring容器中不会被创建，只会将其当做bean定义的模板
 *   从容器中查找abstract为true的bean的时候，会报错BeanIsAbstractException异常
 * 2 属性parent，用来指定当前bean的父bean名称，会继承父bean中定义的配置信息
 * 3 子bean也可以重新定义父bean中已经定义好的配置，这样子bean会覆盖父bean中的配置信息
 */
public class BeanExtendTest {
    @Test
    public void normalBean() {
        String beanXml = "classpath:/com/zj/demo12/normalBean.xml";
        ApplicationContext context = new ClassPathXmlApplicationContext(beanXml);
        System.out.println("serviceB:" + context.getBean(ServiceB.class));
        System.out.println("serviceC:" + context.getBean(ServiceC.class));
    }

    @Test
    public void extendBean() {
        String beanXml = "classpath:/com/zj/demo12/extendBean.xml";
        ApplicationContext context = new ClassPathXmlApplicationContext(beanXml);
        System.out.println("serviceB:" + context.getBean(ServiceB.class));
        System.out.println("serviceC:" + context.getBean(ServiceC.class));

        System.out.println(context.getBean("baseService"));
    }
}
