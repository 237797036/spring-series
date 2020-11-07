package com.zj.demo18;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Map;

/**
 * ElementType.TYPE_USE，能用在任何类型名称上
 */
@Target({ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@interface Ann10 {
    String value();
}

@Ann10("用在了类上")
public class UserAnnotation10<@Ann10("用在了泛型变量V1上") V1, @Ann10("用在了泛型变量V2上") V2> {

    private Map<@Ann10("用在了泛型类型上") String, Integer> map;

    public <@Ann10("用在了参数上") T> String m1(String name) {
        return null;
    }

}