package com.zl.test19.demo3;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.support.StaticMessageSource;

import java.util.Locale;

public class MessageSourceFromDb extends StaticMessageSource implements InitializingBean {

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("start call afterPropertiesSet");
        //此处我们在当前bean初始化之后，模拟从db中获取国际化信息，然后调用addMessage来配置国际化信息
        this.addMessage("desc", Locale.CHINA, "我是从db来的信息");
        this.addMessage("desc", Locale.UK, "MessageSource From Db");
    }
}
