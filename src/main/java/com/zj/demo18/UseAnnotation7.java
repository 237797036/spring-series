package com.zj.demo18;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.TypeVariable;
import java.util.Arrays;

/**
 * ElementType.TYPE_PARAMETER，标注类型参数(泛型变量)，类型参数一般用在类声明或者方法声明；不能用在方法返回值、方法形参、字段上
 */
@Target(value = {ElementType.TYPE_PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@interface Ann7 {
    String value();
}

// 类和方法上面可以声明泛型类型的变量
public class UseAnnotation7<@Ann7("T0是在类上声明的一个泛型变量") T0, @Ann7("T1是在类上声明的一个泛型变量") T1> {

    public <@Ann7("T2是在方法上声明的泛型变量") T2> void m1() {
    }

    // T2、T3是在方法上声明的泛型类型变量
    public <@Ann7("T3是在方法上声明的泛型变量") T3> String m1(String name) {
        return null;
    }

    //下面是错误的用法：不能用在方法返回值、方法形参、字段上（其实也叫泛型类型-跟泛型变量不同）
    /*List<@Ann7("String类型") String> list;
    public Map<@Ann7("String类型") Integer, @Ann7("Double类型") Double> m1(List<@Ann7("String类型") String> list) {
        return null;
    }*/

    public static void main(String[] args) throws NoSuchMethodException {
        for (TypeVariable typeVariable : UseAnnotation7.class.getTypeParameters()) {
            print(typeVariable);
        }

        System.out.println();
        for (TypeVariable typeVariable : UseAnnotation7.class.getDeclaredMethod("m1").getTypeParameters()) {
            print(typeVariable);
        }

        System.out.println();
        for (TypeVariable typeVariable : UseAnnotation7.class.getDeclaredMethod("m1", String.class).getTypeParameters()) {
            print(typeVariable);
        }
    }

    private static void print(TypeVariable typeVariable) {
        System.out.println("泛型变量名称:" + typeVariable.getName());
        Arrays.stream(typeVariable.getAnnotations()).forEach(System.out::println);
    }
}

