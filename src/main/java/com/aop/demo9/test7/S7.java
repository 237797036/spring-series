package com.aop.demo9.test7;

import java.lang.annotation.Target;

//案例2
//注解标注在父类上，注解上没有@Inherited，这种情况下，目标类S7无法匹配到
//@Ann7标注在了父类上，但是@Ann7定义的时候没有使用@Inherited，说明之类无法继承父类上面的注解，所以上面的目标类没有被拦截
@Ann7
class S7Parent {
}

public class S7 extends S7Parent {
    public void m1() {
        System.out.println("我是m1");
    }

    public void m2() {
        System.out.println("我是m2");
    }

    public static void main(String[] args) {
        System.out.println(S7.class.getAnnotation(Target.class));
    }
}
