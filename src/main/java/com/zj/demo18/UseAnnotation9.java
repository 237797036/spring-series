package com.zj.demo18;

import java.lang.annotation.Annotation;

public class UseAnnotation9 {
	public static void main(String[] args) {
		for (Annotation annotation : UseAnnotation9.class.getPackage().getAnnotations()) {
			System.out.println(annotation);
		}
	}
}
