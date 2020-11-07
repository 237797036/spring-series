package com.zj.demo12;

public class ServiceB {
	private String name;
	private ServiceA serviceA;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ServiceA getServiceA() {
		return serviceA;
	}

	public void setServiceA(ServiceA serviceA) {
		this.serviceA = serviceA;
	}

	@Override
	public String toString() {
		return "ServiceB{" +
				"name='" + name + '\'' +
				", serviceA=" + serviceA +
				'}';
	}
}
