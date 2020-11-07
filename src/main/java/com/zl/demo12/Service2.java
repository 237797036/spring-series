package com.zl.demo12;

import org.springframework.stereotype.Component;

@Component
public class Service2 {
    public Service2() {
        System.out.println("create " + this.getClass());
    }
}
