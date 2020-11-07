package com.zj.demo6;

public class UserModel {
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "UserModel{" +
				"name='" + name + '\'' +
				'}';
	}
}
