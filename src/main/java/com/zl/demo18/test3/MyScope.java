package com.zl.demo18.test3;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;

import java.lang.annotation.*;

// 自定义一个bean作用域的注解
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Scope(BeanMyScope.SCOPE_MY) // @1指定作用域的名字
public @interface MyScope {
    /**
     * @see Scope#proxyMode()
     */
    // @2 @Scope注解也有个参数，spring容器解析时，会将这个参数值赋给@Scope注解的proxyMode参数，
    // 此处设置proxyMode值，会直接改变@Scope中proxyMode参数的值。
    ScopedProxyMode proxyMode() default ScopedProxyMode.TARGET_CLASS;
}
