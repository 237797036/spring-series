package com.zj.demo5;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 本文主要讲解了xml中bean的依赖注入，都是采用硬编码的方式进行注入的，这种算是手动的方式
 * <p>
 * 注入普通类型通过value属性或者value元素设置注入的值；注入对象如果是容器的其他bean的时候，需要使用ref属性或者ref元素或者内置bean元素的方式
 * <p>
 * xml(手动)注入缺点：
 * 如果需要注入的对象比较多，比如A类中有几十个属性，那么上面的property属性是不是需要写几十个，此时配置文件代码量暴增
 * <p>
 * 如果A类中新增或者删除了一些依赖，还需要手动去调整bean xml中的依赖配置信息，否则会报错
 * <p>
 * 总的来说就是不利于维护和扩展
 * <p>
 * 为了解决上面这些问题，spring为我们提供了更强大的功能：自动注入
 * 自动注入采用约定大于配置的方式实现
 */
public class DiTest {

    /**
     * 通过构造器的参数位置注入
     * <p>
     * 参数位置的注入对参数顺序有很强的依赖性，若构造函数参数位置被人调整过，会导致注入出错。
     * <p>
     * 不过通常情况下，不建议去在代码中修改构造函数，如果需要新增参数的，可以新增一个构造函数来实现，这算是一种扩展，不会影响目前已有的功能
     */
    @Test
    public void diByConstructorParamIndex() {
        String beanXml = "classpath:/com/zj/demo5/diByConstructorParamIndex.xml";
        ClassPathXmlApplicationContext context = IocUtils.context(beanXml);
        System.out.println(context.getBean("diByConstructorParamIndex"));
    }

    /**
     * 通过构造器的参数类型注入
     * <p>
     * 按照参数位置或者按照参数的类型注入，都有一个问题，很难通过bean配置文件，知道这个参数对应UserModel中的那个属性，代码可读性不好
     */
    @Test
    public void diByConstructorParamType() {
        String beanXml = "classpath:/com/zj/demo5/diByConstructorParamType.xml";
        ClassPathXmlApplicationContext context = IocUtils.context(beanXml);
        System.out.println(context.getBean("diByConstructorParamType"));
    }

    /**
     * 通过构造器的参数名称注入
     */
    @Test
    public void diByConstructorParamName() {
        String beanXml = "classpath:/com/zj/demo5/diByConstructorParamName.xml";
        ClassPathXmlApplicationContext context = IocUtils.context(beanXml);
        System.out.println(context.getBean("diByConstructorParamName"));
    }

    /**
     * 通过setter方法注入
     * setter注入相对于构造函数注入要灵活一些，构造函数需要指定对应构造函数中所有参数的值，
     * 而setter注入的方式没有这种限制，不需要对所有属性都进行注入，可以按需进行注入
     */
    @Test
    public void diBySetter() {
        String beanXml = "classpath:/com/zj/demo5/diBySetter.xml";
        ClassPathXmlApplicationContext context = IocUtils.context(beanXml);
        System.out.println(context.getBean("diBySetter"));
    }


    /**
     * 上面介绍的都是注入普通类型的对象，都是通过value属性来设置需要注入的对象的值的，
     * value属性的值是String类型的，spring容器内部自动会将value的值转换为对象的实际类型
     *
     * 若我们依赖的对象是容器中的其他bean对象的时候，需要用下面的方式进行注入
     *
     * 1 ref属性方式
     * 构造器方式，将value替换为ref；setter方式，将value替换为ref
     *
     * 2 内置bean的方式
     * <constructor-arg>
     *     <bean class=""/>
     * </constructor-arg>
     *
     * <property name="属性名称">
     *     <bean class=""/>
     * </property>
     */

    /**
     * 注入容器中的bean
     */
    @Test
    public void diBean() {
        String beanXml = "classpath:/com/zj/demo5/diBean.xml";
        ClassPathXmlApplicationContext context = IocUtils.context(beanXml);
        System.out.println(context.getBean("diBeanByConstructor"));
        System.out.println(context.getBean("diBeanBySetter"));
    }

    /**
     * 其他各种类型注入
     */
    @Test
    public void diOtherType() {
        String beanXml = "classpath:/com/zj/demo5/diOtherType.xml";
        ClassPathXmlApplicationContext context = IocUtils.context(beanXml);
        System.out.println(context.getBean("diOtherType"));
    }

}
