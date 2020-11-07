package com.zj.demo25.test2;

import org.springframework.context.annotation.Conditional;

@Conditional(EnvCondition.class)
public class Service {
}
