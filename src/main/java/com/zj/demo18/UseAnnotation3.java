package com.zj.demo18;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 只有一个参数，名称为value的时候，使用时参数时名称可以省略
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@interface Ann3 {
    String value();//@1 注解只有一个名称为value的参数
}

@Ann3("我是路人甲java") //@2 使用注解，参数名称value省略
public class UseAnnotation3 {

}
