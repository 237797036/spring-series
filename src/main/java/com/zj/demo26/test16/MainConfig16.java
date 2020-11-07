package com.zj.demo26.test16;

import com.zj.demo26.test14.Service1;
import com.zj.demo26.test14.Service2;
import com.zj.demo26.test14.Service3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MainConfig16 {
    @Bean
    public Service3 service3_0(@Autowired(required = false) Service1 s1, @Autowired(required = false) Service2 s2) { //@0
        Service3 service3 = new Service3();
        service3.setService1(s1); //@1
        service3.setService2(s2); //@2
        return service3;
    }
}
