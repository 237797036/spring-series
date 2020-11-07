package com.zj.demo24.test5;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Module1Config {
    @Bean
    public String name() {
        return "公众号：路人甲java";
    }

    @Bean
    public String address() {
        return "上海市";
    }
}
