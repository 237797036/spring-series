package com.zj.demo17;

import com.zj.demo5.IocUtils;
import org.junit.Test;
import org.springframework.context.ApplicationContext;

public class ConfigTest {

    @Test
    public void test() {
        String beanXml = "classpath:/com/zj/demo17/beans.xml";
        ApplicationContext context = IocUtils.context(beanXml);
        System.out.println(context.getBean(Config.class));
        System.out.println(context.getBean(Config.class));
    }

}
