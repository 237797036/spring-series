package com.zj.demo24.test6;

import org.springframework.stereotype.Component;

@Component
public class Service2 {
    public void m1() {
        System.out.println(this.getClass() + ".m1()");
    }
}
