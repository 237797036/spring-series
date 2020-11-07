package com.zj.demo8;

import com.zj.demo5.IocUtils;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Arrays;
import java.util.Map;

/**
 * bean元素的primary属性可以解决什么问题？
 * <p>
 * 从容器中查找一个bean时，如果容器中出现多个Bean候选者，可通过primary="true"将当前bean置为首选者，
 * 那查找时会返回主要的候选者，否则将抛出异常。
 * <p>
 * 当候选者中如果有多个bean都将primary置为true，此时spring还是会懵逼的，也会报错。
 * <p>
 * 2 primary默认值都是false
 * autowire-candidate的默认值是true
 *
 * 解决：
 * a 将某个候选bean的primary置为true
 * b 或者只保留一个bean的autowire-candidate为true，将其余的满足条件的bean的autowire-candidate置为false
 */
public class PrimaryTest {
    @Test
    public void normalBean() {
        String beanXml = "classpath:/com/zj/demo8/normalBean.xml";
        ClassPathXmlApplicationContext context = IocUtils.context(beanXml);
        //下面我们通过spring容器的T getBean(Class<T> requiredType)方法获取容器中对应的bean
        NormalBean.IService service = context.getBean(NormalBean.IService.class); //@1
        System.out.println(service);
    }

    @Test
    public void setterBean() {
        String beanXml = "classpath:/com/zj/demo8/setterBean.xml";
        ClassPathXmlApplicationContext context = IocUtils.context(beanXml);
    }

    @Test
    public void primaryBean() {
        String beanXml = "classpath:/com/zj/demo8/primaryBean.xml";
        ClassPathXmlApplicationContext context = IocUtils.context(beanXml);
        PrimaryBean.IService service = context.getBean(PrimaryBean.IService.class); //@1
        System.out.println(service);
        PrimaryBean primaryBean = context.getBean(PrimaryBean.class); //@2
        System.out.println(primaryBean);
        //返回与给定对象类型（包括子类）匹配的bean实例。
        //返回一个具有匹配bean的Map，其中包含bean名称作为键，并包含对应的bean实例作为值
        System.out.println(context.getBeansOfType(PrimaryBean.IService.class));
    }

    @Test
    public void eagerlyBean() {
        String beanXml = "classpath:/com/zj/demo8/eagerlyBean.xml";
        ClassPathXmlApplicationContext context = IocUtils.context(beanXml);
        Arrays.stream(context.getBeanNamesForType(String.class, false, true)).forEach(System.out::println);
        System.out.println("----------------");
        Map<String, String> beansOfType = context.getBeansOfType(String.class, true, true);
        beansOfType.forEach((beanName, bean) -> {
            System.out.println(beanName + "->" + bean);
        });
    }
}
