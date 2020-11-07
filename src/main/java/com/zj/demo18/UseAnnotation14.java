package com.zj.demo18;

import org.junit.Test;
import org.springframework.core.annotation.AliasFor;
import org.springframework.core.annotation.AnnotatedElementUtils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// AliasFor注解中value和attribute互为别名，随便设置一个，同时会给另外一个设置相同的值

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@interface A14 {
    String value() default "a";//@1
}

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@A14 //@6 要加在当前注解上
@interface B14 { //@2

    String value() default "b";//@3

    // 一、 将B14注解中的a14Value参数，作为A14注解中value参数的别名。
    // 相当于通过B14给A14注解的value设置值
    @AliasFor(annotation = A14.class, value = "value") //@5
    String a14Value();
}

@B14(value = "路人甲Java", a14Value = "通过B14给A14的value参数赋值") //@4
public class UseAnnotation14 {
    @Test
    public void test1() {
        //AnnotatedElementUtils是spring提供的一个查找注解的工具类
        System.out.println(AnnotatedElementUtils.getMergedAnnotation(UseAnnotation14.class, B14.class));
        System.out.println(AnnotatedElementUtils.getMergedAnnotation(UseAnnotation14.class, A14.class));
    }
}
