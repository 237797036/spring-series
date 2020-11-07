package com.zj.demo18;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 子类可以继承父类中被@Inherited修饰的注解，注意是继承父类中的，
 * 如果接口中的注解使用@Inherited修饰，那实现类无法继承这个注解
 */
public class InheritAnnotationTest {
    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    @Inherited
    @interface A1 { //@1 表示这个注解具有继承功能
    }

    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    @Inherited
    @interface A2 { //@2 表示这个注解具有继承功能
    }

    @A1 //@3
    interface I1 {
    }

    @A2 //@4
    static class C1 {
    }

    // 不能继承接口上被@Inherited修饰的注解
    static class C2 extends C1 implements I1 {
    } //@5

    public static void main(String[] args) {
        // 获取C2上以及从父类继承的所有注解
        for (Annotation annotation : C2.class.getAnnotations()) {
            System.out.println(annotation);
        }
    }
}
