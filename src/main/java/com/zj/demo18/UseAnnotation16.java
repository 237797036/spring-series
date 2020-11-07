package com.zj.demo18;

import org.junit.Test;
import org.springframework.core.annotation.AliasFor;
import org.springframework.core.annotation.AnnotatedElementUtils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@interface A16 {
    String name() default "a";//@0
}

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@A16
@interface B16 { //@1

    // 当@AliasFor中不指定value或attribute时，自动将@AliasFor修饰的参数作为value和attribute的值，
    // 如下：@AliasFor注解的value参数值为name
    @AliasFor(annotation = A16.class) //@5
    String name() default "b";//@2
}

@B16(name = "我是v1") //@3
public class UseAnnotation16 {

    @Test
    public void test1() {
        //AnnotatedElementUtils是spring提供的一个查找注解的工具类
        System.out.println(AnnotatedElementUtils.getMergedAnnotation(UseAnnotation16.class, A16.class));
        System.out.println(AnnotatedElementUtils.getMergedAnnotation(UseAnnotation16.class, B16.class));
    }
}

/**
 * 注解是干什么的？
 * 一个注解可以使用多次么？如何使用？
 * 注解@Inherited是做什么的？
 * 注解@Target中的TYPE_PARAMETER和TYPE_USER用在什么地方？ 泛型中如何使用注解？
 * 注解定义可以实现继承么？
 * spring中对注解有哪些增强？@AliasFor注解是干什么的？
 */
