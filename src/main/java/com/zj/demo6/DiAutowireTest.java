package com.zj.demo6;

import com.zj.demo5.IocUtils;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Collection;
import java.util.List;

/**
 * xml中自动注入配置案例
 */
public class DiAutowireTest {

    /**
     * isAssignableFrom是Class类中的一个方法
     * c1.isAssignableFrom(c2)：判断c2和c1是否相等，或者c2是否是c1的子类
     */
    @Test
    public void isAssignableFrom() {
        System.out.println(Object.class.isAssignableFrom(Integer.class)); //true
        System.out.println(Object.class.isAssignableFrom(int.class)); //false
        System.out.println(Object.class.isAssignableFrom(List.class)); //true
        System.out.println(Collection.class.isAssignableFrom(List.class)); //true
        System.out.println(List.class.isAssignableFrom(Collection.class)); //false
    }

    /**
     * 按照名称进行注入
     */
    @Test
    public void diAutowireByName() {
        String beanXml = "classpath:/com/zj/demo6/diAutowireByName.xml";
        ClassPathXmlApplicationContext context = IocUtils.context(beanXml);
        System.out.println(context.getBean("diAutowireByName1"));
        System.out.println(context.getBean("diAutowireByName2"));
    }

    /**
     * 按照set方法参数类型进行注入
     * <p>
     * 需要注入的set属性的类型和被注入的bean的类型需要满足isAssignableFrom关系。
     * <p>
     * 按照类型自动装配的时候，如果按照类型找到了多个符合条件的bean，系统会报错。
     */
    @Test
    public void diAutowireByType() {
        String beanXml = "classpath:/com/zj/demo6/diAutowireByType.xml";
        ClassPathXmlApplicationContext context = IocUtils.context(beanXml);
        System.out.println(context.getBean("diAutowireByType1"));
    }

    /**
     * 按照类型注入集合
     */
    @Test
    public void diAutowireByTypeExtend() {
        String beanXml = "classpath:/com/zj/demo6/diAutowireByTypeExtend.xml";
        ClassPathXmlApplicationContext context = IocUtils.context(beanXml);
        //从容器中获取DiAutowireByTypeExtend
        DiAutowireByTypeExtend diAutowireByTypeExtend = context.getBean(DiAutowireByTypeExtend.class);
        //输出diAutowireByTypeExtend中的属性看一下
        System.out.println("serviceList：" + diAutowireByTypeExtend.getServiceList());
        System.out.println("baseServiceList：" + diAutowireByTypeExtend.getBaseServiceList());
        System.out.println("service1Map：" + diAutowireByTypeExtend.getService1Map());
        System.out.println("baseServiceMap：" + diAutowireByTypeExtend.getBaseServiceMap());
    }

    /**
     * 构造函数的方式进行自动注入
     */
    @Test
    public void diAutowireByConstructor() {
        String beanXml = "classpath:/com/zj/demo6/diAutowireByConstructor.xml";
        ClassPathXmlApplicationContext context = IocUtils.context(beanXml);
        System.out.println(context.getBean("diAutowireByConstructor"));
    }

    /**
     * autowire=default
     */
    @Test
    public void diAutowireByDefault() {
        String beanXml = "classpath:/com/zj/demo6/diAutowireByDefault.xml";
        ClassPathXmlApplicationContext context = IocUtils.context(beanXml);
        System.out.println(context.getBean("diAutowireByDefault1"));
        System.out.println(context.getBean("diAutowireByDefault2"));
        System.out.println(context.getBean(DiAutowireByName.class));
    }

}
