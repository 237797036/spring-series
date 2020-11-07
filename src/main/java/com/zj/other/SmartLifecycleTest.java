package com.zj.other;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.SmartLifecycle;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

@Slf4j
public class SmartLifecycleTest {

	@Bean
	public String name() {
		System.out.println("name()");
		return "路人甲Java";
	}

	@Bean
	public SmartLifecycle1 smartLifecycl1() {
		return new SmartLifecycle1();
	}

	@Bean
	public SmartLifecycle2 smartLifecycl2() {
		return new SmartLifecycle2();
	}

	public static void main(String[] args) {
		System.out.println("spring容器启动");
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SmartLifecycleTest.class);
		System.out.println("spring容器启动完毕");
		context.close();
		System.out.println("spring容器已关闭");
	}

	@Slf4j
	public static class SmartLifecycle1 implements SmartLifecycle {

		@Override
		public int getPhase() {
			return 10;
		}

		@Override
		public void start() {
			System.out.println("start");
		}

		@Override
		public void stop() {
			System.out.println("stop");
		}

		@Override
		public boolean isRunning() {
			System.out.println("isRunning");
			return true;
		}
	}

	@Slf4j
	public static class SmartLifecycle2 implements SmartLifecycle {

		@Override
		public int getPhase() {
			return 9;
		}

		@Override
		public void start() {
			System.out.println("start");
		}

		@Override
		public void stop() {
			System.out.println("stop");
		}

		@Override
		public boolean isRunning() {
			System.out.println("isRunning");
			return true;
		}
	}
}
