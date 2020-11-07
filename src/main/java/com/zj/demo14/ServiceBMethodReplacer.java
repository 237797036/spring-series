package com.zj.demo14;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.MethodReplacer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.lang.reflect.Method;

/**
 * serviceB的方法替换者
 */
public class ServiceBMethodReplacer implements MethodReplacer, ApplicationContextAware {

    @Override
    public Object reimplement(Object obj, Method method, Object[] args) throws Throwable {
        return context.getBean(ServiceA.class);
    }

    private ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }
}
