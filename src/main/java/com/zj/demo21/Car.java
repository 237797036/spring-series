package com.zj.demo21;

public class Car {

	private String name;

	public Car(String name) {
		this.name = name;
	}

	public void print() {
		System.out.println("保时捷!");
	}

	@Override
	public String toString() {
		return "Car{" +
				"name='" + name + '\'' +
				'}';
	}
}
