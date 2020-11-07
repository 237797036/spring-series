package com.zj.demo27;

import com.zj.demo27.test1.MainConfig1;
import com.zj.demo27.test1.ServiceA;
import com.zj.demo27.test2.MainConfig2;
import com.zj.demo27.test3.MainConfig3;
import com.zj.demo27.test3.Service1;
import com.zj.demo27.test4.MainConfig4;
import com.zj.demo27.test5.MainConfig5;
import com.zj.demo27.test6.MainConfig6;
import com.zj.demo27.test7.MainConfig7;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Arrays;

/**
 * 资源文件路径的写法：
 * 通常我们的项是采用maven来组织的，配置文件一般会放在resources目录，这个目录的文件被编译后会在target/classes目录中。
 * 
 * spring中资源文件路径最常用的有2种写法：
 * 
 * 以classpath:开头：检索目标为当前项目的classes目录
 * 以classpath*:开头：检索目标为当前项目的classes目录，以及项目中所有jar包中的目录。
 * 
 * 1 相对路径的方式
 * classpath:com/zj/demo27/test5/beans.xml
 * classpath*:com/zj/demo27/test5/beans.xml
 * 2 /：绝对路径的方式
 * classpath:/com/zj/demo27/test5/beans.xml
 * 3 *：文件通配符方式
 * classpath:/com/zj/demo27/test5/beans-*.xml
 * 会匹配test5目录中所有以beans-开头的xml结尾的文件
 * 4 *：目录通配符方式
 * classpath:/com/zj/demo27/一个星号/beans-*.xml
 * 会匹配demo27中所有子目录中所有以beans-开头的xml结尾的文件，注意只包含demo27的子目录，不包含子目录的子目录
 * 5 **：递归任意子目录的方式
 * classpath:/com/zj/两个星号/beans-*.xml
 * 会递归当前目录以及下面任意级的子目录
 */

/**
 * 注解@Scope：定义bean 的作用域；2种用法：第1种：标注在类上；第2种：和@Bean一起标注在方法上
 * 注解@DependsOn：指定当前bean依赖的bean，可以确保在创建当前bean之前，先将依赖的bean创建好；a标注在类上；b和@Bean一起标注在方法上
 * 注解@ImportResource：标注在配置类上，配置类中引入bean定义的配置文件(比如xml)
 * 注解@Lazy：让bean延迟初始化；常见3种用法：a标注在类上；b标注在配置类上，会对配置类中所有的@Bean标注的方法有效；c和@Bean一起标注在方法上
 */
public class Demo27Test {

    @Test
    public void test1() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MainConfig1.class);
        for (int i = 0; i < 2; i++) {
            System.out.println(context.getBean(ServiceA.class));
        }
    }

    @Test
    public void test2() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MainConfig2.class);
        for (int i = 0; i < 2; i++) {
            System.out.println(context.getBean(com.zj.demo27.test2.ServiceA.class));
        }
    }

    @Test
    public void test3() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MainConfig3.class);
        System.out.println(context.getBean(Service1.class));
    }

    @Test
    public void test4() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MainConfig4.class);
        System.out.println(context.getBean(com.zj.demo27.test4.Service1.class));
    }

    @Test
    public void test5() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MainConfig5.class);
        for (String beanName : context.getBeanDefinitionNames()) {
            System.out.println(String.format("%s->%s", beanName, context.getBean(beanName)));
        }
    }

    @Test
    public void test6() {
        System.out.println("准备启动spring容器");
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MainConfig6.class);
        System.out.println("spring容器启动完毕");
        System.out.println(context.getBean(com.zj.demo27.test6.Service1.class));
    }

    @Test
    public void test7() {
        System.out.println("准备启动spring容器");
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MainConfig7.class);
        System.out.println("spring容器启动完毕");

        for (String beanName : Arrays.asList("name", "age", "address")) {
            System.out.println("----------");
            System.out.println("getBean:" + beanName + ",start");
            System.out.println(String.format("%s->%s", beanName, context.getBean(beanName)));
            System.out.println("getBean:" + beanName + ",end");
        }
    }
}
