package com.zj.demo14;


import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 实现了单例bean中使用多例bean。
 * replaced-method：方法替换。使用的3个步骤：
 * 1 定义替换者：自定义一个替换者，替换者需要实现spring中的MethodReplacer接口
 * 当调用目标对象需要被替换的方法时，这个调用请求会被转发到上面的替换者的reimplement方法进行处理。
 * 2 在容器中初始化替换者bean
 * 3 通过replaced-method元素配置目标bean需要被替换的方法
 */
public class ReplacedMethodTest {

    /**
     * replaced-method：方法替换，当调用serviceB中的getServiceA方法时，会对这个方法拦截，把这个调用请求转发到一个替换者处理。
     * 比lookup-method更强大更灵活。
     */
    @Test
    public void replacedMethod() {
        String beanXml = "classpath:/com/zj/demo14/replacedMethod.xml";
        ApplicationContext context = new ClassPathXmlApplicationContext(beanXml);

        System.out.println(context.getBean(ServiceA.class)); //@1
        System.out.println(context.getBean(ServiceA.class)); //@2

        System.out.println("serviceB中的serviceA");
        ServiceB serviceB = context.getBean(ServiceB.class); //@3
        serviceB.say();
        serviceB.say();
    }

}
