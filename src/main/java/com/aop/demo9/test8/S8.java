package com.aop.demo9.test8;

@Ann8
class Car {
}

public class S8 {
    public void m1(Car car) {
        System.out.println("我是m1");
    }

    public void m2(String name, Car car, int age) {
        System.out.println("我是m2");
    }
}
