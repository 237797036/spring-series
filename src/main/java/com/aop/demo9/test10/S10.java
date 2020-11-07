package com.aop.demo9.test10;

//案例2
//定义注解时未使用@Inherited，说明子类无法继承父类上的注解，
// 这个案例中我们将定义一个这样的注解，将注解放在目标类的父类上，来看一下效果
@Ann10
class S10Parent {

    public void m1() {
        System.out.println("我是S10Parent.m1()方法");
    }

    public void m2() {
        System.out.println("我是S10Parent.m2()方法");
    }
}

public class S10 extends S10Parent {

    @Override
    public void m2() {
        System.out.println("我是S10.m2()方法");
    }

    public void m3() {
        System.out.println("我是S10.m3()方法");
    }
}
