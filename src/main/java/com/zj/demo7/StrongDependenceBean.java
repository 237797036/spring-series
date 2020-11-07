package com.zj.demo7;

import org.springframework.beans.factory.DisposableBean;

/**
 * 强依赖的bean创建和销毁顺序
 */
public class StrongDependenceBean {
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

		private Bean1 bean1;

		public Bean2(Bean1 bean1) {
			this.bean1 = bean1;
			System.out.println(this.getClass() + " constructor!");
		}

		@Override
		public void destroy() throws Exception {
			System.out.println(this.getClass() + " destroy()");
		}
	}

	public static class Bean3 implements DisposableBean {

		private Bean2 bean2;

		public Bean3(Bean2 bean2) {
			this.bean2 = bean2;
			System.out.println(this.getClass() + " constructor!");
		}

		@Override
		public void destroy() throws Exception {
			System.out.println(this.getClass() + " destroy()");
		}
	}
}
