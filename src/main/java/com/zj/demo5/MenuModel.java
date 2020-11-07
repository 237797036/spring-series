package com.zj.demo5;

/**
 * 菜单类
 */
public class MenuModel {
	//菜单名称
	private String label;
	//同级别排序
	private Integer theSort;

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Integer getTheSort() {
		return theSort;
	}

	public void setTheSort(Integer theSort) {
		this.theSort = theSort;
	}

	@Override
	public String toString() {
		return "MenuModel{" +
				"label='" + label + '\'' +
				", theSort=" + theSort +
				'}';
	}
}
