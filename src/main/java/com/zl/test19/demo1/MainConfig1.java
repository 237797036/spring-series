package com.zl.test19.demo1;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

// 向容器注册国际化的bean，必须是MessageSource类型，bean名称必须为messageSource
@Configuration
public class MainConfig1 {

    @Bean
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource result = new ResourceBundleMessageSource();
        //指定国际化化配置文件的位置，格式：路径/文件名称。注意不包含【语言_国家.properties】这部分
        result.setBasenames("com/zl/demo19/message"); //@1
        return result;
    }
}
