package com.zj.demo1;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 */
public class Client {

	public static void main(String[] args) {
		//1.bean配置文件位置
		String beanXml = "classpath:/com/zj/demo1/beans.xml";

		//2.创建ClassPathXmlApplicationContext容器，给容器指定需要加载的bean配置文件
		ApplicationContext context = new ClassPathXmlApplicationContext(beanXml);

		//3.从容器中获取需要的bean
		HelloWorld helloWorld = context.getBean("helloWorld", com.zj.demo1.HelloWorld.class);

		//4.使用对象
		helloWorld.say();

	}
}
