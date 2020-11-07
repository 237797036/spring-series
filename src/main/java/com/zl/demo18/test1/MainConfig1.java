package com.zl.demo18.test1;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

@ComponentScan
@PropertySource({"classpath:com/zl/demo18/db.properties"})
public class MainConfig1 {
}
