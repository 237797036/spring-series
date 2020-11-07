package com.zj.demo21;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({User.class})
public class Config {
	@Bean
	public Car car() {
		return new Car("config car!");
	}
}
