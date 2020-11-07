package com.zj.demo16;

public class UserService implements IUserService {
	@Override
	public void insert(String name) {
		System.out.println(String.format("用户[name:%s]插入成功!", name));
		get();
	}

	@Override
	public String get() {
		System.out.println("查询成功");
		return "get res";
	}
}
