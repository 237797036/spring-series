package com.zm.demo2.test1;

import org.springframework.stereotype.Component;

/**
 * 构造器的方式注入依赖的bean：实例化ServiceA时，需要serviceB，而实例化ServiceB时需要serviceA，
 * 构造器循环依赖无法解决! 创建下面2个对象，无法创建成功！
 */
@Component
public class ServiceA {

    private ServiceB serviceB;

    public ServiceA(ServiceB serviceB) {
        this.serviceB = serviceB;
    }
}
