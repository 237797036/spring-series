package com.zj.service;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AopTestClient {

    public static void main(String[] args) {
        //1.bean配置文件位置
        String beanXml = "classpath:/com/zj/aop/beans.xml";

        //2.创建ClassPathXmlApplicationContext容器，给容器指定需要加载的bean配置文件
        ApplicationContext context = new ClassPathXmlApplicationContext(beanXml);

        //3.从容器中获取需要的bean
        AopTestService aopTestService = context.getBean("aopTestService", AopTestService.class);

        //4.使用对象
        aopTestService.sayHello("ZJ");

    }
}
