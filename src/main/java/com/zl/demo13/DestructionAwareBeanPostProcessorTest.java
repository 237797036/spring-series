package com.zl.demo13;

import org.junit.Test;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.CommonAnnotationBeanPostProcessor;

/**
 * 自定义 {@link org.springframework.beans.factory.config.DestructionAwareBeanPostProcessor}
 */
public class DestructionAwareBeanPostProcessorTest {

    @Test
    public void test1() {
        DefaultListableBeanFactory factory = new DefaultListableBeanFactory();

        //添加自定义的DestructionAwareBeanPostProcessor
        factory.addBeanPostProcessor(new MyDestructionAwareBeanPostProcessor());

        //向容器中注入3个单例bean
        factory.registerBeanDefinition("serviceA1", BeanDefinitionBuilder.genericBeanDefinition(ServiceA.class).getBeanDefinition());
        factory.registerBeanDefinition("serviceA2", BeanDefinitionBuilder.genericBeanDefinition(ServiceA.class).getBeanDefinition());
        factory.registerBeanDefinition("serviceA3", BeanDefinitionBuilder.genericBeanDefinition(ServiceA.class).getBeanDefinition());

        //触发所有单例bean初始化
        factory.preInstantiateSingletons();

        System.out.println("销毁serviceA1");
        //销毁指定的bean
        factory.destroySingleton("serviceA1");

        System.out.println("触发所有单例bean的销毁");
        factory.destroySingletons();
    }

    @Test
    public void test2() {
        DefaultListableBeanFactory factory = new DefaultListableBeanFactory();

        //添加自定义的DestructionAwareBeanPostProcessor
        factory.addBeanPostProcessor(new MyDestructionAwareBeanPostProcessor());
        //将CommonAnnotationBeanPostProcessor加入（继承自InitDestroyAnnotationBeanPostProcessor，又继承自DestructionAwareBeanPostProcessor）
        //会处理bean中标注@PreDestroy注解的方法
        factory.addBeanPostProcessor(new CommonAnnotationBeanPostProcessor());

        //向容器中注入bean
        factory.registerBeanDefinition("serviceB", BeanDefinitionBuilder.genericBeanDefinition(ServiceB.class).getBeanDefinition());

        //触发所有单例bean初始化
        factory.preInstantiateSingletons();

        System.out.println("销毁serviceB开始");
        //销毁指定的bean
        factory.destroySingleton("serviceB");
    }
}
