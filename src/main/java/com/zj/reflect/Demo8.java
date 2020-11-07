package com.zj.reflect;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.WildcardType;
import java.util.List;
import java.util.Map;

public class Demo8 {
    public static class C1 { //@1
    }

    public static class C2 extends C1 { //@2
    }

    public static List<?> m1(Map<? super C2, ? extends C1> map) { //@3
        return null;
    }

    public static void main(String[] args) throws NoSuchMethodException {
        Method m1 = Demo8.class.getMethod("m1", Map.class);

        //获取m1方法参数泛型详细参数信息
        System.out.println("获取m1方法参数泛型详细参数信息");
        Type[] genericParameterTypes = m1.getGenericParameterTypes();
        for (Type genericParameterType : genericParameterTypes) {
            // m1的参数为Map<? super C2, ? extends C1>是‘泛型类型’，对应ParameterizedType接口
            if (genericParameterType instanceof ParameterizedType) { //@4
                ParameterizedType parameterizedType = (ParameterizedType) genericParameterType; //@5
                //获取Map尖括号中的‘泛型参数列表’，对应? super C2 和 ? extends C1，是个通配符类型，对应WildcardType接口
                Type[] actualTypeArguments = parameterizedType.getActualTypeArguments(); //@6
                for (Type actualTypeArgument : actualTypeArguments) {
                    if (actualTypeArgument instanceof WildcardType) {
                        WildcardType wildcardType = (WildcardType) actualTypeArgument;
                        //获取通配符名称，输出是? super com.zj.reflect.Demo7$C2 和 ? extends com.zj.reflect.Demo7$C1
                        System.out.println("通配符类型名称:" + wildcardType.getTypeName());//@7
                        //获取通配符的上边界
                        Type[] upperBounds = wildcardType.getUpperBounds();
                        for (Type upperBound : upperBounds) {
                            System.out.println("通配符上边界类型：" + upperBound.getTypeName());
                        }
                        //获取通配符的下边界
                        Type[] lowerBounds = wildcardType.getLowerBounds();
                        for (Type lowerBound : lowerBounds) {
                            System.out.println("通配符下边界类型:" + lowerBound.getTypeName());
                        }
                        //如果指定了上边界就没有下边界，如果指定了下边界就有上边界java.lang.Object
                        System.out.println("------------");
                    }
                }
            }
        }

        //获取返回值通配符详细信息
        System.out.println("获取m1方法返回值泛型类型详细信息");
        Type genericReturnType = m1.getGenericReturnType();
        // m1的返回值是List<?>，是个‘泛型类型’，对应ParameterizedType接口，泛型中的具体类型是个通配符类型，对应WildcardType接口
        if (genericReturnType instanceof ParameterizedType) { //@4
            ParameterizedType parameterizedType = (ParameterizedType) genericReturnType; //@5
            //获取List尖括号中的'泛型参数列表'，对应?，是个通配符类型，对应WildcardType接口
            Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
            for (Type actualTypeArgument : actualTypeArguments) {
                if (actualTypeArgument instanceof WildcardType) {
                    WildcardType wildcardType = (WildcardType) actualTypeArgument;
                    //获取通配符的名称，输出是?
                    System.out.println("通配符类型名称:" + wildcardType.getTypeName());
                    //获取通配符的上边界
                    Type[] upperBounds = wildcardType.getUpperBounds();
                    for (Type upperBound : upperBounds) {
                        System.out.println("通配符上边界类型：" + upperBound.getTypeName());
                    }
                    //获取通配符的下边界
                    Type[] lowerBounds = wildcardType.getLowerBounds();
                    for (Type lowerBound : lowerBounds) {
                        System.out.println("通配符下边界类型:" + lowerBound.getTypeName());
                    }
                    //通配符?没有下边界，只有上边界java.lang.Object
                    System.out.println("------------");
                }
            }
        }
    }

}