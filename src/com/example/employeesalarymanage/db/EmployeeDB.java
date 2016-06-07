package com.example.employeesalarymanage.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class EmployeeDB extends SQLiteOpenHelper{

	public EmployeeDB(Context context) {
		super(context, "Employee.db", null, 1);
		
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("create table employee (_id integer primary key autoincrement,id vchar(10),name text,salary vchar(10))");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("drop table employee");
		onConfigure(db);
	}

}
