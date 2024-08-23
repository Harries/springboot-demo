package com.et.exception.model;

import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;

public class User implements Serializable {
	private static final long serialVersionUID = 1L;
	 private int id;
	 private String name;
	 private int age;
	 
	 public User(){
	 }

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String toString() {
		return JSONObject.toJSONString(this);
	}
}
