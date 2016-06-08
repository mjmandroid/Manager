package com.example.employeesalarymanage.activity;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.example.employeesalarymanage.R;
import com.example.employeesalarymanage.adapter.MyAdapter;
import com.example.employeesalarymanage.base.BaseActivity;
import com.example.employeesalarymanage.domain.EmployeeBean;
import com.example.employeesalarymanage.view.LoadingPage.LoadResult;

public class ResultActivity extends Activity{
	private ArrayList<EmployeeBean> list;
	private MyAdapter adapter;

	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.result_activity);
		adapter = new MyAdapter(this);
		Intent intent = getIntent();
		if(intent != null){
			list = (ArrayList<EmployeeBean>) intent.getSerializableExtra("list");
			ListView listview = (ListView) findViewById(R.id.lv);
			listview.setAdapter(adapter);
			adapter.setDatas(list);
		}
	}
	
}
