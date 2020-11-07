package com.zj.demo20;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Arrays;

/**
 * 1 注解@Configuration这个注解可以加在类上，让这个类等同于一个bean xml配置文件
 * 2 通过AnnotationConfigApplicationContext加载@Configuration修饰的类
 * AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ConfigBean.class);
 * 此时ConfigBean类中没有任何内容，相当于一个空的xml配置文件，若要在ConfigBean类中注册bean，需要@Bean注解
 * 3 注解@Bean类似于bean xml文件中的bean元素，在spring容器中注册一个bean。
 * 用在方法上，表示通过此方法定义一个bean，默认将方法名称作为bean名称，方法返回值作为bean对象，注册到spring容器
 * 4 被@Configuration修饰的类，也被注册到spring容器中
 */
public class ConfigurationTest {

    @Test
    public void test1() {
        //通过AnnotationConfigApplicationContext加载配置类ConfigBean，会将配置类中所有的bean注册到spring容器中
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ConfigBean.class); //@1
        for (String beanName : context.getBeanDefinitionNames()) {
            //别名
            String[] aliases = context.getAliases(beanName);
            System.out.println(String.format("bean名称:%s,别名:%s,bean对象:%s",
                    beanName,
                    Arrays.asList(aliases),
                    context.getBean(beanName)));
        }
    }

    /**
     * 区别：
     * 1 有没有@Configuration注解，@Bean都会起效，都会将@Bean修饰的方法作为bean注册到容器中
     * 2 被@Configuration修饰的bean带有EnhancerBySpringCGLIB字样，没有@Configuration注解的bean没有Cglib的字样；
     * 有EnhancerBySpringCGLIB说明这个bean被cglib处理过，变成了一个代理对象。
     */

    /**
     * 本质区别：有@Configuration注解的类，被@Bean修饰的方法都只被调用了一次
     * 1 被@Configuration修饰的类，spring容器中通过cglib给这个类创建一个代理，代理会拦截所有被@Bean修饰的方法
     * 2 默认情况（bean为单例）下确保被@Bean修饰的方法只被调用一次，即这些bean都是同一个bean
     */

    @Test
    public void test2() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ConfigBean1.class);
        for (String beanName : context.getBeanDefinitionNames()) {
            //别名
            String[] aliases = context.getAliases(beanName);
            System.out.println(String.format("bean名称:%s,别名:%s,bean对象:%s",
                    beanName,
                    Arrays.asList(aliases),
                    context.getBean(beanName)));
        }
    }

    @Test
    public void test3() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ConfigBean2.class);
        for (String beanName : context.getBeanDefinitionNames()) {
            //别名
            String[] aliases = context.getAliases(beanName);
            System.out.println(String.format("bean名称:%s,别名:%s,bean对象:%s",
                    beanName,
                    Arrays.asList(aliases),
                    context.getBean(beanName)));
        }
    }
}