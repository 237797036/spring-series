package com.aop.demo9.test14;

import org.aspectj.lang.annotation.Pointcut;

public class AspectPcDefine {

    @Pointcut("bean(bean1)")
    public void pc1() {
    }

    @Pointcut("bean(bean2)")
    public void pc2() {
    }
}
