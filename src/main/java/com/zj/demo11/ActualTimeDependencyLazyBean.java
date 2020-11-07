package com.zj.demo11;

public class ActualTimeDependencyLazyBean {

	public ActualTimeDependencyLazyBean() {
		System.out.println("ActualTimeDependencyLazyBean实例化!");
	}

	private LazyInitBean lazyInitBean;

	public LazyInitBean getLazyInitBean() {
		return lazyInitBean;
	}

	public void setLazyInitBean(LazyInitBean lazyInitBean) {
		this.lazyInitBean = lazyInitBean;
		System.out.println("ActualTimeDependencyLazyBean.setLazyInitBean方法!");
	}
}
