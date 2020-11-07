package com.aop.demo2;

import org.springframework.cglib.core.DefaultNamingPolicy;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.NoOp;

public class NamingPolicyTest {
    public static void main(String[] args) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(NamingPolicyTest.class);
        enhancer.setCallback(NoOp.INSTANCE);
        //通过Enhancer.setNamingPolicy来设置代理类的命名策略
        enhancer.setNamingPolicy(new DefaultNamingPolicy() {
            @Override
            protected String getTag() {
                return "-test-";
            }
        });
        Object proxy = enhancer.create();
        System.out.println(proxy.getClass());
    }
}
