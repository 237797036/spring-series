package com.zj.demo18;

import org.junit.Test;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;

// 1.定义容器注解
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD})
@interface Ann12s {
    Ann12[] value(); //@1 容器注解必须有个value类型的参数，参数类型为子注解类型的数组
}

// 2.为注解指定容器
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD})
@Repeatable(Ann12s.class) //@2 要让一个注解重复使用，需在注解上加@Repeatable注解，@Repeatable中value值为‘容器注解’
@interface Ann12 {
    String name();
}

// 3.重复使用相同的注解：有2种方式
@Ann12(name = "路人甲Java")
@Ann12(name = "Spring系列")
public class UseAnnotation12 {
    @Ann12s(
            {@Ann12(name = "Java高并发系列"),
                    @Ann12(name = "mysql高手系列")}
    )
    private String v1;

    // 4.获取注解信息
    @Test
    public void test1() throws NoSuchFieldException {
        Annotation[] annotations = UseAnnotation12.class.getAnnotations();
        for (Annotation annotation : annotations) {
            System.out.println(annotation);
        }
        System.out.println("-------------");
        Field v1 = UseAnnotation12.class.getDeclaredField("v1");
        Annotation[] declaredAnnotations = v1.getDeclaredAnnotations();
        for (Annotation declaredAnnotation : declaredAnnotations) {
            System.out.println(declaredAnnotation);
        }
    }
    // 截止到目前都是java中的注解。另外spring对注解有相应的增强
}