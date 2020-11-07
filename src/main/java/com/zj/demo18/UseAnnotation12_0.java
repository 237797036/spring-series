package com.zj.demo18;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@interface Ann12_0 {
}

// 会报错。
// UseAnnotation12_0重复使用了@Ann12注解。默认情况下：@Ann12注解不允许重复使用
/*@Ann12_0*/
/*@Ann12_0*/
public class UseAnnotation12_0 {
}