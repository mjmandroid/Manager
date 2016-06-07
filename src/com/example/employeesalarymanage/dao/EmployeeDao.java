package com.example.employeesalarymanage.dao;

import java.util.ArrayList;
import java.util.List;

import com.example.employeesalarymanage.db.EmployeeDB;
import com.example.employeesalarymanage.domain.EmployeeBean;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

public class EmployeeDao {
	private Context mContext;
	private EmployeeDB db;

	public EmployeeDao(Context mContext) {
		this.mContext = mContext;
		db = new EmployeeDB(mContext);
	}

	public void add(String name, String id, String salary) {
		SQLiteDatabase database = db.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put("id", id);
		contentValues.put("salary", salary);
		contentValues.put("name", name);
		database.insert("employee", null, contentValues);
		database.close();
	}

	public void delete(String id) {
		SQLiteDatabase database = db.getWritableDatabase();
		database.delete("employee", "id=?", new String[] { id + "" });
		database.close();
	}

	public void updata(String name, String id, String salary) {
		SQLiteDatabase database = db.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put("salary", salary);
		contentValues.put("name", name);
		int a = database.update("employee", contentValues,
				"id"+"=?", new String[] {id});
		database.close();
	}

	public List<EmployeeBean> quary(String n) {
		SQLiteDatabase database = db.getWritableDatabase();
		Cursor cursor = database.query("employee", null, null, null, null,
				null, null);
		List<EmployeeBean> list = new ArrayList<EmployeeBean>();
		while (cursor.moveToNext()) {
			String id = cursor.getString(cursor.getColumnIndex("id"));
			String name = cursor.getString(cursor.getColumnIndex("name"));
			String salary = cursor.getString(cursor.getColumnIndex("salary"));
			if (n.equals(name)) {
				EmployeeBean bean = new EmployeeBean(id, name, salary);
				list.add(bean);
			}
		}
		database.close();
		return list;
	}

	public List<EmployeeBean> quary() {
		SQLiteDatabase database = db.getWritableDatabase();
		Cursor cursor = database.query("employee", null, null, null, null,
				null, null);
		List<EmployeeBean> list = new ArrayList<EmployeeBean>();
		while (cursor.moveToNext()) {
			String id = cursor.getString(cursor.getColumnIndex("id"));
			String name = cursor.getString(cursor.getColumnIndex("name"));
			String salary = cursor.getString(cursor.getColumnIndex("salary"));
			EmployeeBean bean = new EmployeeBean(id, name, salary);
			list.add(bean);
		}
		database.close();
		return list;
	}
}
