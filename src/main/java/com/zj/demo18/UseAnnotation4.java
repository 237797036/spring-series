package com.zj.demo18;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 数组类型参数
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@interface Ann4 {
    String[] name();//@1 name是一个String类型的数组
}

@Ann4(name = {"我是路人甲java", "欢迎和我一起学spring"}) //@2 name有多个值时，需要使用{}包含起来
public class UseAnnotation4 {
    @Ann4(name = "如果只有一个值，{}可以省略") //@3 如果name只有一个值，{}可以省略
    public class T1 {
    }
}
