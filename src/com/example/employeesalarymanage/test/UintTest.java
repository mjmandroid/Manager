package com.example.employeesalarymanage.test;

import com.example.employeesalarymanage.dao.EmployeeDao;

import android.test.AndroidTestCase;

public class UintTest extends AndroidTestCase{
	public void test(){
		EmployeeDao dao = new EmployeeDao(getContext());
		for (int i = 0; i < 50; i++) {
			dao.add("逼哥"+i,"123456"+i ,i+100+"");
		}
		
	}
}
