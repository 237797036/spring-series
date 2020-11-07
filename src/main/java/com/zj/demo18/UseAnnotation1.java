package com.zj.demo18;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@interface Ann1 { //@1 Ann1为无参注解
}

@Ann1 //@2 类上使用@Ann1注解，没有参数
public class UseAnnotation1 {

}
