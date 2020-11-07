package com.zj.reflect;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class Demo5<T1, T2> { //@1
    public void m1(Demo5<T1, T2> demo) { //@2
        //demoClass对应的是Demo5$1的Class对象
        Class<? extends Demo5> demoClass = demo.getClass();
        System.out.println(demoClass);

        //获取当前类父类的具体类型信息，如果父类是泛型，则会返回泛型详细信息
        Type genericSuperclass = demoClass.getGenericSuperclass();
        // 泛型类型在Java中用ParameterizedType接口表示
        System.out.println(genericSuperclass.getClass());

        if (genericSuperclass instanceof ParameterizedType) {
            ParameterizedType pt = (ParameterizedType) genericSuperclass;
            System.out.println(pt.getRawType());
            Type[] actualTypeArguments = pt.getActualTypeArguments();
            for (Type actualTypeArgument : actualTypeArguments) {
                System.out.println(actualTypeArgument.getTypeName());
            }
            System.out.println(pt.getOwnerType());
        }
    }

    public static void main(String[] args) {
        Demo5<String, Integer> demo5 = new Demo5<String, Integer>() {
        };//@3 创建了一个匿名内部类，相当于创建了Demo5的一个子类，并且指定了Demo5中两个泛型变量的具体类型
        demo5.m1(demo5);
    }
}