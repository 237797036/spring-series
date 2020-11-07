package com.zj.demo20;

public class ServiceB {
	private ServiceA serviceA;

	public ServiceB(ServiceA serviceA) {
		this.serviceA = serviceA;
	}

	@Override
	public String toString() {
		return "ServiceB{" +
				"serviceA=" + serviceA +
				'}';
	}
}
