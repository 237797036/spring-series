package com.aop.demo9.test11;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited //注意多了个继承，即此注解放在父类上，可被子类继承
public @interface Ann11 {
}

