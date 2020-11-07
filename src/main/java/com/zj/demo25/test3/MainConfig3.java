package com.zj.demo25.test3;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

@Conditional(MyCondition1.class)
@Configuration
public class MainConfig3 {
    @Bean
    public String name() {
        return "路人甲Java";
    }
}
