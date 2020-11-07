package com.zj.demo27.test6;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy
public class Service1 {
    public Service1() {
        System.out.println("创建Service1");
    }
}
