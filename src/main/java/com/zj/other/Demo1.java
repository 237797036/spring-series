package com.zj.other;


import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.lang.Nullable;

public class Demo1 {
	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("com/zj/other/demo1.xml");
		System.out.println(context.getBean("myFactoryBean"));
	}

	public static class MyFactoryBean implements FactoryBean<String> {
		private String target;

		public String getTarget() {
			return target;
		}

		public void setTarget(String target) {
			this.target = target;
		}


		@Nullable
		@Override
		public String getObject() throws Exception {
			return this.target;
		}

		@Nullable
		@Override
		public Class<?> getObjectType() {
			return String.class;
		}
	}
}
