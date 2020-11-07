package com.zj.demo7;

import com.zj.demo5.IocUtils;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 1 无依赖的bean，创建顺序和定义的顺序一致，销毁顺序刚好相反
 * 2 通过构造器注入强依赖的bean，会先创建构造器参数中对应的bean，然后才会创建当前bean，销毁顺序刚好相反
 * 3 depend-on可以指定当前bean依赖的bean，确保depend-on指定的bean在当前bean创建之前先创建好，销毁顺序刚好相反
 * 4 通过setter注入强依赖的bean，会先创建当前bean，然后再创建依赖的bean， 销毁顺序和bean创建的顺序相同
 */
public class DependOnTest {

    /**
     * 无依赖的bean创建和销毁的顺序
     * <p>
     * 1 bean对象的创建顺序和bean xml中定义的顺序一致
     * <p>
     * 2 bean销毁的顺序和bean xml中定义的顺序相反
     */
    @Test
    public void normalBean() {
        System.out.println("容器启动中!");
        String beanXml = "classpath:/com/zj/demo7/normalBean.xml";
        ClassPathXmlApplicationContext context = IocUtils.context(beanXml);
        System.out.println("容器启动完毕，准备关闭spring容器!");
        //关闭容器
        context.close();
        System.out.println("spring容器已关闭!");
    }

    /**
     * 1 如果xml中定义的bean特别多，而有些bean之间没有强依赖关系，此时若去调整bean的创建和销毁顺序，
     *  得去调整xml中bean的定义顺序，或去加强依赖，这样是非常不好的!
     * 2 spring中可以通过depend-on来解决，在不调整bean定义顺序和强加依赖的情况下，可通过depend-on属性来设置当前bean依赖于哪些bean，
     *   可以保证depend-on指定的bean在当前bean之前先创建好，销毁的时候在当前bean之后进行销毁。
     */

    /**
     * 通过构造器注入强依赖的bean的创建和销毁顺序测试
     */
    @Test
    public void strongDependenceBean() {
        System.out.println("容器启动中!");
        String beanXml = "classpath:/com/zj/demo7/strongDependenceBean.xml";
        ClassPathXmlApplicationContext context = IocUtils.context(beanXml);
        System.out.println("容器启动完毕，准备关闭spring容器! 通过 构造方法 注入强依赖");
        context.close();
        System.out.println("spring容器已关闭!");
    }

    /**
     * 通过setter方式注入强依赖的bean的创建和销毁顺序测试
     */
    @Test
    public void StrongDependenceBean1() {
        System.out.println("容器启动中!");
        String beanXml = "classpath:/com/zj/demo7/strongDependenceBean1.xml";
        ClassPathXmlApplicationContext context = IocUtils.context(beanXml);
        System.out.println("容器启动完毕，准备关闭spring容器! 通过 setter 注入强依赖");
        context.close();
        System.out.println("spring容器已关闭!");
    }

    /**
     * 通过depend-on来干预bean创建和销毁顺序
     */
    @Test
    public void dependOnBean() {
        System.out.println("容器启动中!");
        String beanXml = "classpath:/com/zj/demo7/dependOnBean.xml";
        ClassPathXmlApplicationContext context = IocUtils.context(beanXml);
        System.out.println("容器启动完毕，准备关闭spring容器!");
        context.close();
        System.out.println("spring容器已关闭!");
    }

}
