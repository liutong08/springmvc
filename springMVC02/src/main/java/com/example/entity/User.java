package com.example.entity;

public class User {

	private String name;
	private int age;
	private String province;

	@Override
	public String toString() {
		return "User [name=" + name + ", age=" + age + ", province=" + province + "]";
	}

	public User() {
		super();
	}

	public User(String name, int age, String province) {
		super();
		this.name = name;
		this.age = age;
		this.province = province;
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

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}
}
