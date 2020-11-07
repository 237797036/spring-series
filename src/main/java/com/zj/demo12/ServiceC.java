package com.zj.demo12;

public class ServiceC {
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
		return "ServiceC{" +
				"name='" + name + '\'' +
				", serviceA=" + serviceA +
				'}';
	}
}
