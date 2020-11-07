package com.aop.demo9.test9;

// 案例1
//目标对象上有@within中指定的注解，这种情况时，目标对象的所有方法都会被拦截。
@Ann9
public class S9 {

    public void m1() {
        System.out.println("我是m1方法");
    }
    public void m2() {
        System.out.println("我是m2方法");
    }
}
