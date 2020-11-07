package com.zj.demo25.test2;

import org.springframework.context.annotation.Conditional;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// 注解EnvConditiona上使用了@Conditional注解，用到了一个自定义Condition类：EnvCondition
@Conditional(EnvCondition.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface EnvConditional {
    //环境(测试环境、开发环境、生产环境)
    enum Env {
        TEST, DEV, PROD
    }

    //环境
    Env value() default Env.DEV;
}
