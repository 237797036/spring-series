package com.zj.demo21;


import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Client {
	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
		for (String s : context.getBeanDefinitionNames()) {
			System.out.println(s + "->" + context.getBean(s));
		}

		Car car = context.getBean(Car.class);
		car.print();
	}
}
