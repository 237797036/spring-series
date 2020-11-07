package com.zj.service;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 */
public class ZjDyTestClient {

    public static void main(String[] args) {
        //1.bean配置文件位置
        String beanXml = "classpath:/com/zj/aop/beans.xml";

        //2.创建ClassPathXmlApplicationContext容器，给容器指定需要加载的bean配置文件
        ApplicationContext context = new ClassPathXmlApplicationContext(beanXml);

        //3.从容器中获取需要的bean
        ZjDyService zjDyService = context.getBean("zjDyService", ZjDyService.class);

        //4.使用对象
        try {
            zjDyService.sayHello("ZJ", true);
        } catch (Exception e) {
            System.out.println("main print:" + e.getMessage());
        }

    }
}
