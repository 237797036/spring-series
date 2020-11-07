package com.aop.demo9.test6;

//案例1
//注解直接标注在目标类上，这种情况目标类会被匹配到。
@Ann6
public class S6 {
    public void m1() {
        System.out.println("我是m1");
    }

    public void m2() {
        System.out.println("我是m2");
    }
}
