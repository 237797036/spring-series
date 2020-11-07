package com.zj.demo21;

import org.springframework.context.annotation.Bean;

public class User {
	@Bean
	public Car car() {
		return new Car("user car!");
	}
}
