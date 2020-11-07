package com.zj.demo9;

import com.zj.demo5.IocUtils;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * bean元素的autowire-candidate可以设置当前bean是否作为其他bean自动注入的候选bean
 * <p>
 * 异常：org.springframework.beans.factory.NoUniqueBeanDefinitionException
 * 原因：当从容器中按照类型查找一个bean对象时，容器中却找到了多个匹配的bean，此时spring不知道如何选择，就会报这个异常。
 * 主要出现在2种场景中：
 * 1 从容器容器中查找符合指定类型的bean，对应BeanFactory下面的方法：T getBean(Class<T> requiredType) throws BeansException;
 * 2 自动注入方式设置为byType的时
 */
public class AutowireCandidateTest {

    @Test
    public void setterBean() {
        String beanXml = "classpath:/com/zj/demo9/autowireCandidateBean.xml";
        ClassPathXmlApplicationContext context = IocUtils.context(beanXml);
        System.out.println(context.getBean(SetterBean.class)); //@1

        SetterBean.IService service = context.getBean(SetterBean.IService.class); //@2
        System.out.println(service);
    }

    @Test
    public void setterBean2() {
        String beanXml = "classpath:/com/zj/demo9/autowireCandidateBean.xml";
        ClassPathXmlApplicationContext context = IocUtils.context(beanXml);

        SetterBean.IService service = context.getBean(SetterBean.IService.class); //@1
        System.out.println(service);
        System.out.println();

        System.out.println(context.getBean(SetterBean.class)); //@2

    }

}
