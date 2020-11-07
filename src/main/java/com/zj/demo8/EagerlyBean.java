package com.zj.demo8;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.lang.Nullable;

public class EagerlyBean {
	public static class FactoryBean1 implements FactoryBean<String> {

		@Nullable
		@Override
		public String getObject() throws Exception {
			System.out.println(this.getClass() + "#FactoryBean1()");
			return "路人甲Java";
		}

		@Nullable
		@Override
		public Class<?> getObjectType() {
			System.out.println(this.getClass() + "#getObjectType()");
			return String.class;
		}

		@Override
		public boolean isSingleton() {
			return false;
		}
	}
}
