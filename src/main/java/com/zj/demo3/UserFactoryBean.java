package com.zj.demo3;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.lang.Nullable;

/**
 * 通过FactoryBean来创建bean对象：
 * getObject方法内部由开发者去实现对象的创建，然后将创建好的对象返回给Spring容器；
 * getObjectType需指定我们创建的bean的类型；
 * isSingleton表示通过这个接口创建的对象是否是单例的，如果为false，每次从容器中获取对象时都会调用getObject()生成bean对象。
 */
public class UserFactoryBean implements FactoryBean<UserModel> {

	int count = 1;

	@Nullable
	@Override
	public UserModel getObject() throws Exception {
		UserModel userModel = new UserModel();
		userModel.setName("我是通过FactoryBean创建的第" + count++ + "对象");
		return userModel;
	}

	@Nullable
	@Override
	public Class<?> getObjectType() {
		return UserModel.class;
	}

	@Override
	public boolean isSingleton() {
		return false;
		/*return true;*/
	}
}
