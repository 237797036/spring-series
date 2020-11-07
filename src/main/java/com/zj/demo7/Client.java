package com.zj.demo7;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.context.annotation.Conditional;
import org.springframework.core.type.AnnotatedTypeMetadata;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Collection;

public class Client {
	@Qualifier
	@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.TYPE, ElementType.ANNOTATION_TYPE})
	@Retention(RetentionPolicy.RUNTIME)
	@Inherited
	@Documented
	public @interface X {
		String name() default "";
	}

	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		context.register(Client.class);
		context.refresh();
	}

	@Autowired
	public void list1(@Qualifier("user1") Collection<User> list0, Collection<User> list1) {
		System.out.println(list0);
		System.out.println(list1);
	}

	@Bean
	@Qualifier("user1")
	public User user1() {
		return new User("1");
	}

	@Bean
	public User user2() {
		return new User("2");
	}

	@Bean(autowireCandidate = false)
	@BeanClass({"user1", "user2"})
	public User user3() {
		return new User("3");
	}

	@Conditional(Condition1.class)
	@Target({ElementType.TYPE, ElementType.METHOD})
	@Retention(RetentionPolicy.RUNTIME)
	@Documented
	public @interface BeanClass {
		String[] value();
	}

	public static class Condition1 implements Condition {

		@Override
		public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
			return true;
		}
	}

	public static class User {
		private String name;

		public User() {
		}

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
}
