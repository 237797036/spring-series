package com.zj.demo25.test7;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig1 {
    @Bean
    public Service service() {
        return new Service();
    }
}
