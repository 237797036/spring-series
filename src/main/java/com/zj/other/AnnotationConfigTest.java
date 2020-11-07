package com.zj.other;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AnnotationConfigTest {

	@Autowired
	private User user1;

	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AnnotationConfigTest.class);
		AnnotationConfigTest bean = context.getBean(AnnotationConfigTest.class);
		System.out.println(bean.user1);
	}

	public static class User {
		private String name;

		public User(String name) {
			this.name = name;
		}

		@Override
		public String toString() {
			return "User{" +
					"name='" + name + '\'' +
					'}';
		}
	}

	@Bean
	public User user2() {
		return new User("2");
	}

	@Bean
	public User user1() {
		return new User("1");
	}

}
