package com.zj.demo18;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.List;
import java.util.Map;

@Target(value = {ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@interface Ann8 {
    String value();
}

public class UseAnnotation8<@Ann8("T0是在类上声明的一个泛型变量") T0, @Ann8("T1是在类上声明的一个泛型类型变量") T1> {

    public <@Ann8("T2是在方法上声明的泛型变量") T2> void m1() {
    }

    List<@Ann8("String类型") String> list;
    List<@Ann8("通配符类型") ?> list2;
    Map<@Ann8("Integer类型") Integer, @Ann8("Double类型") Double> map;

    public Map<@Ann8("Integer类型") Integer, @Ann8("Double类型") Double> m1(List<@Ann8("String类型") String> list) {
        this.list = list;
        return null;
    }
}