package com.zj.demo4;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * bean作用域
 */
public class ScopeTest {

    ApplicationContext context;

    /**
     * 在所有@Test标注的方法之前之前运行
     */
    @Before
    public void before() {
        System.out.println("spring容器准备启动.....");
        //1.bean配置文件位置
        String beanXml = "classpath:/com/zj/demo4/beans.xml";
        //2.创建ClassPathXmlApplicationContext容器，给容器指定需要加载的bean配置文件
        this.context = new ClassPathXmlApplicationContext(beanXml);
        System.out.println("spring容器启动完毕！");
    }

    /**
     * 1. 单例bean：spring容器默认创建的bean实例是单例的。
     * a.通常spring容器在'启动'的时候，会将scope为singleton的bean创建好放在容器中
     * b.有个特殊情况，当bean的lazy被设置为true的时候，表示懒加载，那么使用的时候才会创建
     * <p>
     * 单例bean是整个应用共享的，所以需要考虑到线程安全问题(springMvc中controller默认是单例的)
     */
    @Test
    public void singletonBean() {
        System.out.println("---------单例bean，每次获取的bean实例都一样---------");
        System.out.println(context.getBean("singletonBean"));
        System.out.println(context.getBean("singletonBean"));
        System.out.println(context.getBean("singletonBean"));
    }

    /**
     * 2. 多例bean
     * 容器启动过程中并没有创建BeanScopeModel对象
     * 每次获取的时候才会去调用构造方法创建bean实例
     * <p>
     * 多例bean每次获取的时候都会重新创建，如果这个bean比较复杂，创建时间比较长，会影响系统的性能
     */
    @Test
    public void prototypeBean() {
        System.out.println("---------多例bean，每次获取的bean实例都不一样---------");
        System.out.println();
        System.out.println(context.getBean("prototypeBean"));
        System.out.println(context.getBean("prototypeBean"));
        System.out.println(context.getBean("prototypeBean"));
    }

    /**
     * web容器环境：
     * request、session、application都是在spring web容器环境中才会有
     * 3. request: 对每个http请求都会创建一个bean实例，request结束的时候，这个bean也就结束
     * 4. session: 每个会话会对应一个bean实例，不同的session对应不同的bean实例
     * 5. application: 一个web应用程序对应一个bean实例
     *
     * singleton是每个spring容器只有一个bean实例
     * 一个应用程序可以创建多个spring容器,不同的容器可以存在同名的bean
     * 但是scope=application时，不管应用中有多少个spring容器，这个应用中同名的bean只有一个。
     */

    /**
     * 6. 自定义scope
     *  a. 实现Scope接口 b. 将自定义的scope注册到容器 c. 使用自定义的作用域
     */

}
