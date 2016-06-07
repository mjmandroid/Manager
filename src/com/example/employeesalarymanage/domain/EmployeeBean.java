package com.example.employeesalarymanage.domain;

import java.io.Serializable;

public class EmployeeBean implements Serializable{
	private String id,name,salary;
	private boolean isCheck;
	
	public boolean isCheck() {
		return isCheck;
	}

	public void setCheck(boolean isCheck) {
		this.isCheck = isCheck;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSalary() {
		return salary;
	}

	public void setSalary(String salary) {
		this.salary = salary;
	}

	public EmployeeBean(String id, String name, String salary) {
		this.id = id;
		this.name = name;
		this.salary = salary;
	}
	
}
