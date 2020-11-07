package com.zj.demo18;

import org.junit.Test;
import org.springframework.core.annotation.AliasFor;
import org.springframework.core.annotation.AnnotatedElementUtils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// 同一个注解中使用@AliasFor
@Target({ElementType.TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@interface A15 {
	@AliasFor("v2")//@1
	String v1() default "";

	@AliasFor("v1")//@2
	String v2() default "";

	// 二、 @1 @2 @AliasFor如果不指定annotation的值，那annotation默认值就是当前注解，
	// 上面2个属性互为别名，当给v1设置值时相当于给v2设置值，当给v2设置值时相当于给v1设置值
	// 注意：v1和v2的值始终相等，如果同时给v1和v2设置值会报错
}

@A15(v1 = "我是v1"/*, v2 = "v2new"*/) //@3
public class UseAnnotation15 {

	@A15(v2 = "我是v2") //@4
	private String name;

	@Test
	public void test1() throws NoSuchFieldException {
		//AnnotatedElementUtils是spring提供的一个查找注解的工具类
		System.out.println(AnnotatedElementUtils.getMergedAnnotation(UseAnnotation15.class, A15.class));
		System.out.println(AnnotatedElementUtils.getMergedAnnotation(UseAnnotation15.class.getDeclaredField("name"), A15.class));
	}
}
