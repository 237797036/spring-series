package com.zj.demo24;

import com.zj.demo24.test1.MainConfig1;
import com.zj.demo24.test2.MainConfig2;
import com.zj.demo24.test3.MainConfig3;
import com.zj.demo24.test4.MainConfig4;
import com.zj.demo24.test5.MainConfig5;
import com.zj.demo24.test6.MainConfig6;
import com.zj.demo24.test6.Service1;
import com.zj.demo24.test6.Service2;
import com.zj.demo24.test7.MainConfig7;
import com.zj.demo24.test8.MainConfig8;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 注解@Import是由类org.springframework.context.annotation.ConfigurationClassPostProcessor处理的。
 * 注解@Configuration、@Bean、@ComponentScan、@ComponentScans都是被这个类处理的，这个类是高手必经之路，建议花点时间研究。
 *
 * 注解@Import可以用来批量导入任何普通的组件、配置类，将这些类中定义的所有bean注册到容器中
 * 常见的5种用法需要掌握
 * 掌握ImportSelector、ImportBeanDefinitionRegistrar、DeferredImportSelector的用法
 * DeferredImportSelector接口可以实现延迟导入、按序导入的功能
 * spring中很多以@Enable开头的都是使用@Import集合ImportSelector方式实现的
 * BeanDefinitionRegistry接口：bean定义注册器，这个需要掌握的常见方法
 */
public class ImportTest {
    @Test
    public void test1() {
        //1.通过AnnotationConfigApplicationContext创建spring容器，参数为@Import标注的类
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MainConfig1.class);
        //2.输出容器中定义的所有bean信息
        for (String beanName : context.getBeanDefinitionNames()) {
            System.out.println(String.format("%s->%s", beanName, context.getBean(beanName)));
        }
    }

    @Test
    public void test2() {
        //1.通过AnnotationConfigApplicationContext创建spring容器，参数为@Import标注的类
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MainConfig2.class);
        //2.输出容器中定义的所有bean信息
        for (String beanName : context.getBeanDefinitionNames()) {
            System.out.println(String.format("%s->%s", beanName, context.getBean(beanName)));
        }
    }

    @Test
    public void test3() {
        //1.通过AnnotationConfigApplicationContext创建spring容器，参数为@Import标注的类
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MainConfig3.class);
        //2.输出容器中定义的所有bean信息
        for (String beanName : context.getBeanDefinitionNames()) {
            System.out.println(String.format("%s->%s", beanName, context.getBean(beanName)));
        }
    }

    @Test
    public void test4() {
        //1.通过AnnotationConfigApplicationContext创建spring容器，参数为@Import标注的类
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MainConfig4.class);
        //2.输出容器中定义的所有bean信息
        for (String beanName : context.getBeanDefinitionNames()) {
            System.out.println(String.format("%s->%s", beanName, context.getBean(beanName)));
        }
    }

    @Test
    public void test5() {
        //1.通过AnnotationConfigApplicationContext创建spring容器，参数为@Import标注的类
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MainConfig5.class);
        //2.输出容器中定义的所有bean信息
        for (String beanName : context.getBeanDefinitionNames()) {
            System.out.println(String.format("%s->%s", beanName, context.getBean(beanName)));
        }
    }

    @Test
    public void test6() {
        //1.通过AnnotationConfigApplicationContext创建spring容器，参数为@Import标注的类
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MainConfig6.class);
        Service1 service1 = context.getBean(Service1.class);
        Service2 service2 = context.getBean(Service2.class);
        service1.m1();
        service2.m1();
    }

    /**
     * springboot中@EnableAutoConfiguration就是靠DeferredImportSelector实现
     * DeferredImportSelector是ImportSelector的子接口，也可以通过@Import进行导入，和ImportSelector不同有两点：
     * 1 延迟导入：
     * 注解@Import的value假设包含普通类、@Configuration标注的配置类、ImportSelector接口的实现类、ImportBeanDefinitionRegistrar接口的实现类、
     * 还有DeferredImportSelector接口实现类等，
     * 会将DeferredImportSelector类型的放在最后处理，会先处理其他被导入的类，其他类会按照value所在的前后顺序进行注册。
     *
     * 注解@Conditional，可按条件注册bean，比如可以判断某个bean不存在的时候才进行注册，等等各种条件判断，
     * 通过@Conditional结合DeferredImportSelector可做很多事情
     *
     *
     * 2 指定导入的类的处理顺序：
     * 当@Import中有多个DeferredImportSelector接口的实现类时，可以指定‘他们’的注册顺序。2种方式：
     * a 实现Ordered接口
     * b 实现Order注解
     * 都是value的值越小，优先级越高
     */
    @Test
    public void test7() {
        //1.通过AnnotationConfigApplicationContext创建spring容器，参数为@Import标注的类
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MainConfig7.class);
    }

    /**
     * 当@Import中有多个DeferredImportSelector接口的实现类时，可以指定他们的顺序，指定顺序常见2种方式
     *
     * 实现Ordered接口的方式
     */
    @Test
    public void test8() {
        //1.通过AnnotationConfigApplicationContext创建spring容器，参数为@Import标注的类
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MainConfig8.class);
    }
}
