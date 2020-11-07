package com.zl.demo17.module2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Service3 {

    //使用模块2中的Service1
    @Autowired
    private com.zl.demo17.module2.Service1 service1;

    //使用模块1中的Service2
    @Autowired
    private com.zl.demo17.module1.Service2 service2;

    public String m1() {
        return this.service2.m1();
    }

    public String m2() {
        return this.service1.m2();
    }

}
