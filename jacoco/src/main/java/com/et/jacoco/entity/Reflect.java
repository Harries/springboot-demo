package com.et.jacoco.entity;

import com.et.jacoco.ShippingService;

import java.lang.reflect.Field;

/**
 * @ClassName Reflect
 * @Description TODO
 * @Author liuhaihua
 * @Date 2024/7/20 18:03
 * @Version 1.0
 */
public class Reflect {
	public static void main(String[] args) throws IllegalAccessException {
		for (Field f : User.class.getDeclaredFields()) {

			String g = f.getName();

			//过滤jacoco编译期间加入的 JacocoData 字段
			//if (f.isSynthetic()) {
			//	continue;
			//}
			//if (f.get(user) != null) {
				System.out.println("fieldname:"+g);
			//}

		}
	}
}
