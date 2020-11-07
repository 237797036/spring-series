package com.zl.test19.demo3;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MainConfig3 {
    @Bean
    public MessageSource messageSource(){
        System.out.println("start new MessageSourceFromDb");
        return new MessageSourceFromDb();
    }
}
