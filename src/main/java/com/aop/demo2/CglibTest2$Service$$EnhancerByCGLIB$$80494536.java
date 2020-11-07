package com.aop.demo2;

import com.aop.demo2.CglibTest2.IService1;
import com.aop.demo2.CglibTest2.IService2;
import com.aop.demo2.CglibTest2.Service;

public class CglibTest2$Service$$EnhancerByCGLIB$$80494536 extends Service implements IService1, IService2 {
    @Override
    public void m1() {
        long starttime = System.nanoTime();
        super.m1();
        System.out.println("方法m1，耗时(纳秒):" + (System.nanoTime() - starttime));
    }

    @Override
    public void m2() {
        long starttime = System.nanoTime();
        super.m1();
        System.out.println("方法m1，耗时(纳秒):" + (System.nanoTime() - starttime));
    }
}
