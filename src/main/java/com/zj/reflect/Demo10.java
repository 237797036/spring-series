package com.zj.reflect;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.List;
import java.util.Map;


/**
 * 1、Demo10<K, V>：  --------> 对应java中的Class对象
 * 2、<K, V>：定义了2个泛型变量，泛型变量对应TypeVariable接口
 * 3、Map<String, ? extends List<? extends Map<K, V>>> [][]map：定义了一个二维泛型数组，泛型数组用GenericArrayType接口表示
 * 4、map中的每个元素是Map<String, ? extends List<? extends Map<K, V>>> []类型，是一个一维泛型数组，泛型数组用GenericArrayType接口表示。
 * 5、再继续拆解，Map<String, ? extends List<? extends Map<K, V>>> []中每个元素是Map<String, ? extends List<? extends Map<K, V>>>泛型类型的，泛型类型在java中使用ParameterizedType接口表示
 * 6、拆解Map<String, ? extends List<? extends Map<K, V>>>后面尖括号中的参数列表，可以调用ParameterizedType接口的Type[] getActualTypeArguments()方法获取，Map后面的尖括号中有2个参数，分别是String和? extends List<? extends Map<K, V>>
 * 7、String是java中定义的类型，对应java中的Class对象
 * 8、? extends List<? extends Map<K, V>>是通配符类型，对应WildcardType接口，通配符指定了上边界，上边界是List<? extends Map<K, V>>
 * 9、List<? extends Map<K, V>>又是一个泛型类型，泛型类型对应ParameterizedType接口，List<? extends Map<K, V>>的尖括号中又定义了这个泛型类型的具体的类型? extends Map<K, V>
 * 10、? extends Map<K, V>又是一个通配符类型，对应WildcardType接口,这个通配符指定了上边界为Map<K,V>
 * 11、Map<K,V>又对应泛型类型，泛型类型对应ParameterizedType接口，调用这个接口的getActualTypeArguments()方法获取泛型中的参数列表K和V
 * 12、K和V是Demo10中定义的泛型变量类型，泛型变量类型对应TypeVariable接口
 *
 * @param <K>
 * @param <V>
 */

public class Demo10<K, V> {

    Map<String, ? extends List<? extends Map<K, V>>>[][] map;

    public static void parseType(Type type, int level) {
        String whileString = whileString(level);
        if (type instanceof GenericArrayType) {
            System.out.println(whileString + "泛型数组类型:" + type);
            parseType(((GenericArrayType) type).getGenericComponentType(), ++level);
        } else if (type instanceof ParameterizedType) {
            System.out.println(whileString + "泛型类型:" + type);
            ParameterizedType parameterizedType = (ParameterizedType) type;
            System.out.println(whileString + "实际类型:" + parameterizedType.getRawType());
            Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
            System.out.println(whileString + actualTypeArguments.length + "个泛型参数,如下：");
            int count = 0;
            for (Type actualTypeArgument : actualTypeArguments) {
                if (count++ == 0) {
                    level++;
                }
                parseType(actualTypeArgument, level);
            }
        } else if (type instanceof WildcardType) {
            System.out.println(whileString + "通配符类型:" + type);
            WildcardType wildcardType = ((WildcardType) type);
            System.out.println(whileString + "通配符类型名称:" + wildcardType.getTypeName());
            Type[] upperBounds = wildcardType.getUpperBounds();
            System.out.println(whileString + "上边界列表");
            int count = 0;
            for (Type upperBound : upperBounds) {
                if (count++ == 0) {
                    level++;
                }
                parseType(upperBound, level);
            }
            System.out.println(whileString + "下边界列表");
            Type[] lowerBounds = wildcardType.getLowerBounds();
            for (Type lowerBound : lowerBounds) {
                if (count++ == 0) {
                    level++;
                }
                parseType(lowerBound, level);
            }
        } else if (type instanceof TypeVariable) {
            System.out.println(whileString + "泛型变量类型:" + type);
            TypeVariable typeVariable = ((TypeVariable) type);
            Type[] bounds = typeVariable.getBounds();
            System.out.println(whileString + "泛型变量上边界列表");
            int count = 0;
            for (Type bound : bounds) {
                if (count++ == 0) {
                    level++;
                }
                parseType(bound, level);
            }
        } else if (type instanceof Class) {
            System.out.println(whileString + "普通类型:" + ((Class) type).getName());
        }
    }

    public static String whileString(int count) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < count; i++) {
            sb.append("----");
        }
        return sb.toString();
    }

    public static void main(String[] args) throws NoSuchFieldException {
        parseType(Demo10.class.getDeclaredField("map").getGenericType(), 0);
    }
}