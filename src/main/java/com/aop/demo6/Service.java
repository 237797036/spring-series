package com.aop.demo6;

public class Service implements IService {
    @Override
    public void say(String name) {
        System.out.println("hello：" + name);
    }
}
