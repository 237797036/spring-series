package com.zj.demo13;

import com.zj.demo13.normal.ServiceA;
import com.zj.demo13.normal.ServiceB;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 单例bean中使用多例bean，3种方式：
 * 1 lookup-method：方法查找，可以对指定bean的方法进行拦截，然后从容器中查找指定的bean作为被拦截方法的返回值
 * 2 replaced-method：方法替换，可以实现bean方法替换的效果，整体比lookup-method更灵活
 * 3 让bean实现接口ApplicationContextAware，在bean对象中就可以使用容器的任何方法
 */
public class LookupMethodTest {

    /**
     * 一、通常：我们使用的bean都是单例的，如果一个bean需要依赖于另一个bean（即使是原型）时，可以在当前bean中声明另外一个bean引用，
     * 然后注入依赖的bean，此时被依赖/被注入的bean在当前bean中自始至终都是同一个实例。
     */
    @Test
    public void normalBean() {
        String beanXml = "classpath:/com/zj/demo13/normalBean.xml";
        ApplicationContext context = new ClassPathXmlApplicationContext(beanXml);

        System.out.println(context.getBean(ServiceA.class)); //@1
        System.out.println(context.getBean(ServiceA.class)); //@2

        // serviceB是单例，serviceB中的serviceA会在容器创建serviceB时，从容器中获取一个serviceA将其注入到serviceB中，
        // 自始至终serviceB中的serviceA都是同一个对象
        System.out.println("serviceB中的serviceA");//b是单例的，a是多例
        ServiceB serviceB = context.getBean(ServiceB.class); //@3
        System.out.println(serviceB.getServiceA()); //@4
        System.out.println(serviceB.getServiceA()); //@5
    }

    /**
     * 二、 如果希望beanB(单例)中每次使用beanA(原型)时，beanA都是一个新的实例，怎么实现呢？
     * <p>
     * 可以在serviceB中加个方法去获取serviceA，这个方法中主动去容器中获取serviceA，那每次获取的都是不同的serviceA实例。
     * 那如何在serviceB中获取到spring容器呢？
     * <p>
     * spring中有个接口ApplicationContextAware，这个接口有个方法：setApplicationContext(ApplicationContext applicationContext)
     * <p>
     * 如果bean实现了这个接口，spring容器创建bean时，会自动调用setApplicationContext方法，将容器对象applicationContext传入，
     * 此时在bean对象中就可以使用容器的任何方法。
     * <p>
     * 缺点：
     * 用spring中的接口ApplicationContextAware，对spring的api有耦合作用，推行高内聚低耦合，所以得寻求更好的办法。
     */

    @Test
    public void applicationContextAware() {
        String beanXml = "classpath:/com/zj/demo13/applicationContextAware.xml";
        ApplicationContext context = new ClassPathXmlApplicationContext(beanXml);

        System.out.println(context.getBean(com.zj.demo13.applicationContextAware.ServiceA.class)); //@1
        System.out.println(context.getBean(com.zj.demo13.applicationContextAware.ServiceA.class)); //@2

        System.out.println("serviceB中的serviceA");
        com.zj.demo13.applicationContextAware.ServiceB serviceB = context.getBean(com.zj.demo13.applicationContextAware.ServiceB.class); //@3
        serviceB.say();//serviceB是一个对象，而serviceA是不同的对象
        serviceB.say();//serviceB是一个对象，而serviceA是不同的对象
    }

    /**
     * 三、lookup-method：方法查找，调用name属性指定的方法时，spring会拦截这个方法，然后去容器中查找lookup-method元素中bean属性指定的bean实例，
     * 然后将找到的bean作为方法的返回值返回。底层是使用cglib代理实现。
     */
    @Test
    public void lookupMethod() {
        String beanXml = "classpath:/com/zj/demo13/lookupMethod.xml";
        ApplicationContext context = new ClassPathXmlApplicationContext(beanXml);

        System.out.println(context.getBean(com.zj.demo13.lookupMethod.ServiceA.class)); //@1
        System.out.println(context.getBean(com.zj.demo13.lookupMethod.ServiceA.class)); //@2

        System.out.println("serviceB中的serviceA");
        com.zj.demo13.lookupMethod.ServiceB serviceB = context.getBean(com.zj.demo13.lookupMethod.ServiceB.class); //@3
        serviceB.say();
        serviceB.say();
    }

    /**
     * 四、replaced-method：方法替换，当调用serviceB中的getServiceA方法时，会对这个方法拦截，把这个调用请求转发到一个替换者处理。
     * 比lookup-method更强大更灵活。
     */

}
