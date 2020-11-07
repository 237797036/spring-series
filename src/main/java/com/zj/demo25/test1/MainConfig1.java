package com.zj.demo25.test1;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({BeanConfig2.class, BeanConfig1.class})
public class MainConfig1 {
}
