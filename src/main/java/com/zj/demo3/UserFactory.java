package com.zj.demo3;

public class UserFactory {

	public UserModel buildUser1() {
		System.out.println("实例工厂创建1");
		UserModel userModel = new UserModel();
		userModel.setName("bean实例方法创建的对象!");
		return userModel;
	}

	public UserModel buildUser2(String name, int age) {
		System.out.println("实例工厂创建2");
		UserModel userModel = new UserModel();
		userModel.setName(name);
		userModel.setAge(age);
		return userModel;
	}
}
