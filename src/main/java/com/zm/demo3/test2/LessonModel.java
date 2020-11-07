package com.zm.demo3.test2;

import org.springframework.stereotype.Component;

@Component
public class LessonModel {
    //课程名称
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "LessonModel{" +
                "name='" + name + '\'' +
                '}';
    }
}
