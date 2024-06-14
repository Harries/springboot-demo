package com.et.xstream;

import lombok.Getter;

@Getter
public class Employee { 
	private String firstName; 
	private String lastName; 
	private int salary; 
	private int age; 
	private String gender; 

	public Employee(String firstName, String lastName, 
					int salary, int age, String gender) 
	{ 
		this.firstName = firstName; 
		this.lastName = lastName; 
		this.salary = salary; 
		this.age = age; 
		this.gender = gender; 
	} 
}
