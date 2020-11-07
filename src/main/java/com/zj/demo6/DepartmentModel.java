package com.zj.demo6;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DepartmentModel {
	private Object object;
	private List<UserModel> userModelList;
	private ArrayList<UserModel> userModelArrayList;
	private Map<String, UserModel> userModelMap;

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

	public List<UserModel> getUserModelList() {
		return userModelList;
	}

	public void setUserModelList(List<UserModel> userModelList) {
		this.userModelList = userModelList;
	}

	public ArrayList<UserModel> getUserModelArrayList() {
		return userModelArrayList;
	}

	public void setUserModelArrayList(ArrayList<UserModel> userModelArrayList) {
		this.userModelArrayList = userModelArrayList;
	}

	public Map<String, UserModel> getUserModelMap() {
		return userModelMap;
	}

	public void setUserModelMap(Map<String, UserModel> userModelMap) {
		this.userModelMap = userModelMap;
	}

	@Override
	public String toString() {
		return "DepartmentModel{" +
				"object=" + object +
				", userModelList=" + userModelList +
				", userModelArrayList=" + userModelArrayList +
				", userModelMap=" + userModelMap +
				'}';
	}
}
