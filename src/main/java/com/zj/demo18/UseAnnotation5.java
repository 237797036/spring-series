package com.zj.demo18;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 为参数指定默认值
 * 通过default为参数指定默认值，用时如果没有设置值，则取默认值；没有指定默认值的参数，使用时必须为参数设置值
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@interface Ann5 {
    String[] name() default {"路人甲java", "spring系列"};//@1 数组类型通过{}指定默认值

    int[] score() default 1; //@2 数组类型参数，默认值只有一个，省略了{}符号

    int age() default 30; //@3 默认值为30

    String address(); //@4 未指定默认值
}

@Ann5(age = 32, address = "上海") //@5 age=32对默认值进行了覆盖，并且为address指定了值
public class UseAnnotation5 {

}
