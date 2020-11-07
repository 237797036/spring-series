package com.zj.demo18;

import org.junit.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedParameterizedType;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

/**
 * 1 什么是注解？
 * 注解给编译器和虚拟机用，编译器和虚拟机在运行过程中可以获取注解信息，然后根据这些信息做各种事情。
 * <p>
 * 2 如何使用注解？
 * a.定义注解 b.使用注解 c.获取注解信息做各种牛逼事情
 * *
 * 使用@interface定义注解，有没有参数都可
 * public @interface 注解名称{
 * [public] 参数类型 参数名称1() [default 参数默认值];
 * [public] 参数类型 参数名称2() [default 参数默认值];
 * [public] 参数类型 参数名称n() [default 参数默认值];
 * }
 * <p>
 * 3 关于定义的问题：如何为注解定义参数？ 注解可以用在哪里？ 注解会被保留到什么时候？
 * <p>
 * a 参数：
 * 访问修饰符必须为public，不写默认为public
 * <p>
 * 参数类型只能是基本数据类型、String、Class、枚举类型、注解类型以及上述类型的一维数组
 * <p>
 * 参数名称一般定义为名词，如果注解只有一个参数，请把名字起为value（后面使用会带来便利）
 * 注解只有一个名称为value的参数，使用注解时，参数名称value可省略
 * <p>
 * 参数名称后面的()仅仅是一个特殊的语法
 * <p>
 * default代表默认值，值必须和定义的类型一致；如果没有默认值，代表后续使用注解时必须给该类型元素赋值
 * <p>
 * b 指定注解的使用范围：@Target注解（有一个参数value，是ElementType枚举类型的一维数组）
 * 自定义注解上也可以不使用‘@Target注解’，表示自定义注解可以用在任何地方
 * <p>
 * ElementType枚举：
 * TYPE：类、接口、枚举、注解上面
 * FIELD：字段上
 * METHOD：方法上
 * PARAMETER：方法的参数上
 * CONSTRUCTOR：构造函数上
 * LOCAL_VARIABLE：本地变量上
 * ANNOTATION_TYPE：注解上
 * PACKAGE：包上
 * TYPE_PARAMETER：类型参数上
 * TYPE_USE：类型名称上
 * <p>
 * c 指定注解的保留策略：@Retention注解（有一个value参数，类型为RetentionPolicy枚举）
 * <p>
 * ElementType枚举：
 * SOURCE：注解只保留在源码中，编译为字节码之后就丢失了，即在class字节码文件中不存在
 * CLASS：注解只保留在源码和字节码中，运行阶段会丢失
 * RUNTIME：源码、字节码、运行期间都存在
 */
public class UserAnnotation11Test {

    // 解析类上的注解
    @Test
    public void m1() {
        // 返回此元素上存在的所有注解，包括从父类继承的
        for (Annotation annotation : UseAnnotation11.class.getAnnotations()) {
            System.out.println(annotation);
        }
    }

    // 解析类上的泛型变量
    @Test
    public void m2() {
        // GenericDeclaration：所有声明泛型变量的公共接口。getTypeParameters() 获取声明的泛型变量列表。
        // 泛型变量可以在类和方法中进行声明
        // 任何类可使用Class对象表示，方法可以用Method类表示，Class类和Method类实现了GenericDeclaration接口（注意：Field没有实现），
        // 调用他们的getTypeParameters方法获取其声明的泛型变量列表
        TypeVariable<Class<UseAnnotation11>>[] typeParameters = UseAnnotation11.class.getTypeParameters();
        System.out.println(typeParameters.getClass());
        for (TypeVariable<Class<UseAnnotation11>> typeParameter : typeParameters) {
            System.out.println(typeParameter.getName() + "变量类型注解信息：");
            Annotation[] annotations = typeParameter.getAnnotations();
            for (Annotation annotation : annotations) {
                System.out.println(annotation);
            }
        }
    }

    // 解析字段name上的注解
    @Test
    public void m3() throws NoSuchFieldException {
        Field nameField = UseAnnotation11.class.getDeclaredField("name");
        for (Annotation annotation : nameField.getAnnotations()) {
            System.out.println(annotation);
        }
    }

    // 解析泛型字段map上的注解
    @Test
    public void m4() throws NoSuchFieldException {
        Field field = UseAnnotation11.class.getDeclaredField("map");
        // Class<?> field.getType()获取字段类型所属的Class对象
        // Type field.getGenericType()获取字段的类型，如果是泛型类型，则返回泛型类型的明细；如果不是泛型类型，和getType返回的结果一样
        Type genericType = field.getGenericType();
        System.out.println(genericType);
        Type[] actualTypeArguments = ((ParameterizedType) genericType).getActualTypeArguments();

        AnnotatedType annotatedType = field.getAnnotatedType();
        AnnotatedType[] annotatedActualTypeArguments = ((AnnotatedParameterizedType) annotatedType).getAnnotatedActualTypeArguments();
        int i = 0;
        for (AnnotatedType actualTypeArgument : annotatedActualTypeArguments) {
            Type actualTypeArgument1 = actualTypeArguments[i++];
            System.out.println(actualTypeArgument1.getTypeName() + "类型上的注解如下：");
            for (Annotation annotation : actualTypeArgument.getAnnotations()) {
                System.out.println(annotation);
            }
        }
    }

    // 解析泛型字段map上的注解_2
    @Test
    public void m4_2() throws NoSuchFieldException {
        Field field = UseAnnotation11.class.getDeclaredField("map");
        AnnotatedType annotatedType = field.getAnnotatedType();
        // 返回此泛型类型的带注解的实际类型参数
        if (annotatedType instanceof AnnotatedParameterizedType) {
            AnnotatedType[] annotatedActualTypeArguments = ((AnnotatedParameterizedType) annotatedType).getAnnotatedActualTypeArguments();
            for (AnnotatedType actualTypeArgument : annotatedActualTypeArguments) {
                for (Annotation annotation : actualTypeArgument.getAnnotations()) {
                    System.out.println(annotation);
                }
                System.out.println();
            }
        }

        System.out.println("========以下方式不可行========");
        Annotation[] annotations = field.getAnnotations();
        for (Annotation annotation : annotations) {
            System.out.println(annotation);
        }
    }

    // 解析构造函数上的注解
    @Test
    public void m5() {
        Constructor<?> constructor = UseAnnotation11.class.getConstructors()[0];
        for (Annotation annotation : constructor.getAnnotations()) {
            System.out.println(annotation);
        }
    }

    // 解析m1方法上的注解
    @Test
    public void m6() throws NoSuchMethodException {
        Method method = UseAnnotation11.class.getMethod("m1", String.class);
        for (Annotation annotation : method.getAnnotations()) {
            System.out.println(annotation);
        }
    }

    // 解析m1方法参数注解
    @Test
    public void m7() throws NoSuchMethodException {
        Method method = UseAnnotation11.class.getMethod("m1", String.class);
        for (Parameter parameter : method.getParameters()) {
            // -parameters 反射获取参数名
            System.out.println(String.format("参数%s上的注解如下:", parameter.getName()));
            for (Annotation annotation : parameter.getAnnotations()) {
                System.out.println(annotation);
            }
        }
    }
}
