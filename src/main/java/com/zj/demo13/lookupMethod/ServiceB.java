package com.zj.demo13.lookupMethod;

public class ServiceB {

	public void say() {
		ServiceA serviceA = getServiceA();
		System.out.println("this:" + this + ",serviceA:" + serviceA);
	}

	public ServiceA getServiceA() { //@1
		return null;
	}

}
