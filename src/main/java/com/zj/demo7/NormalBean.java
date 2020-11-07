package com.zj.demo7;

import org.springframework.beans.factory.DisposableBean;

/**
 * 无任何依赖的bean创建的顺序
 */
public class NormalBean {

	/**
	 * DisposableBean
	 * bean类可以实现这个接口，当调用容器的close()关闭容器时，spring会调用容器中所有bean的destroy()方法，用来做一些清理的工作
	 */
	public static class Bean1 implements DisposableBean {

		public Bean1() {
			System.out.println(this.getClass() + " constructor!");
		}

		@Override
		public void destroy() throws Exception {
			System.out.println(this.getClass() + " destroy()");
		}
	}

	public static class Bean2 implements DisposableBean {

		public Bean2() {
			System.out.println(this.getClass() + " constructor!");
		}

		@Override
		public void destroy() throws Exception {
			System.out.println(this.getClass() + " destroy()");
		}
	}

	public static class Bean3 implements DisposableBean {

		public Bean3() {
			System.out.println(this.getClass() + " constructor!");
		}

		@Override
		public void destroy() throws Exception {
			System.out.println(this.getClass() + " destroy()");
		}
	}
}
