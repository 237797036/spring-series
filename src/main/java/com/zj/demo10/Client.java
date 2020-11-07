package com.zj.demo10;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Arrays;

public class Client {
	public static void main(String[] args) {
		String beanXml = "classpath:/com/zj/demo10/beans.xml";
		ApplicationContext context = new ClassPathXmlApplicationContext(beanXml);
		Arrays.stream(context.getBeanDefinitionNames()).forEach(System.out::println);
	}
}
