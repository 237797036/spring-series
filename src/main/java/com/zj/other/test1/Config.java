package com.zj.other.test1;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Configuration
public class Config {

	@Component
	public class jobs {
		@PostConstruct
		public void m1() {
			System.out.println("m1");
		}
	}
}
