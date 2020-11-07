package com.aop.demo9.test13;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
//EnableAspectJAutoProxy这个属于aop中自动代理的范围
@EnableAspectJAutoProxy // 通过AspectJ方式，自动为符合条件的bean创建代理
public class MainConfig13 {

    //将Aspect13注册到spring容器
    @Bean
    public Aspect13 aspect13() {
        return new Aspect13();
    }

    // 为原生对象
    @Bean
    public BeanService beanService1() {
        return new BeanService("beanService1");
    }


    //因为在Aspect13中配置了@Pointcut("bean(beanService2)")，会拦截spring容器中名称为beanService2的bean，并为其创建代理
    @Bean
    public BeanService beanService2() {
        return new BeanService("beanService2");
    }
}
