package com.zj.demo13.applicationContextAware;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 如果我们的bean实现了ApplicationContextAware这个接口，spring容器'创建bean时'，会自动调用setApplicationContext方法，
 * 将容器对象applicationContext传入，此时在我们的bean对象中就可以使用容器的任何方法。
 * <p>
 * 但是用到spring中的接口ApplicationContextAware，对spring的api有耦合作用，我们一直推行高内聚低耦合，所以得寻求更好的办法。
 */
public class ServiceB implements ApplicationContextAware { //@1

    public void say() {
        ServiceA serviceA = getServiceA();//@2
        System.out.println("this:" + this + ",serviceA:" + serviceA);
    }

    public ServiceA getServiceA() {
        return context.getBean(ServiceA.class);//@3
    }

    private ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }
}
