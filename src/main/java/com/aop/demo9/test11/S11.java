package com.aop.demo9.test11;

@Ann11
class S11Parent {

    public void m1() {
        System.out.println("我是S11Parent.m1()方法");
    }

    public void m2() {
        System.out.println("我是S11Parent.m2()方法");
    }
}

public class S11 extends S11Parent {

    @Override
    public void m2() {
        System.out.println("我是S11.m2()方法");
    }

    public void m3() {
        System.out.println("我是S11.m3()方法");
    }
}
