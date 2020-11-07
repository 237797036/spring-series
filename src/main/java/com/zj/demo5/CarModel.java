package com.zj.demo5;

public class CarModel {
    private String name;
    //描述信息
    private String desc;

    public CarModel() {
    }

    /*@ConstructorProperties({"name", "desc"})*/
    public CarModel(String name, String desc) {
        this.name = name;
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "CarModel{" +
                "name='" + name + '\'' +
                ", desc='" + desc + '\'' +
                '}';
    }
}
