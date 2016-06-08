package com.example.employeesalarymanage;

import java.util.ArrayList;
import java.util.List;

import com.example.employeesalarymanage.activity.CalcActivity;
import com.example.employeesalarymanage.activity.ResultActivity;
import com.example.employeesalarymanage.adapter.MyAdapter;
import com.example.employeesalarymanage.base.BaseActivity;
import com.example.employeesalarymanage.dao.EmployeeDao;
import com.example.employeesalarymanage.domain.EmployeeBean;
import com.example.employeesalarymanage.view.LoadingPage.LoadResult;

import android.os.Bundle;
import android.app.AlertDialog;
import android.content.Intent;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends BaseActivity {

	private EmployeeDao dao;
	private MyAdapter adapter;
	private AlertDialog dialog;
	private AlertDialog dialog2;
	private ListView listView;
	private Button btn_select;
	private Button btn_calc;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		adapter = new MyAdapter(this);
		super.onCreate(savedInstanceState);
		show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_add:
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("提示");
			View view = View.inflate(this, R.layout.add, null);
			final EditText tv_id = (EditText) view.findViewById(R.id.et_id);
			final EditText tv_name = (EditText) view.findViewById(R.id.et_name);
			final EditText tv_salary = (EditText) view.findViewById(R.id.et_salary);
			view.findViewById(R.id.btn_yes).setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					if(!TextUtils.isEmpty(tv_id.getText().toString().trim()) &&
							!TextUtils.isEmpty(tv_name.getText().toString().trim()) &&
							!TextUtils.isEmpty(tv_salary.getText().toString().trim())){
						EmployeeBean bean = new EmployeeBean(tv_id.getText().toString().trim(), 
								tv_name.getText().toString().trim(), 
								tv_salary.getText().toString().trim());
						adapter.add(bean);
						dialog.dismiss();
					}
					
				}
			});
			view.findViewById(R.id.btn_cancel).setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					
					dialog.dismiss();
				}
			});
			builder.setView(view);
			builder.setCancelable(false);
			dialog = builder.create();
			dialog.show();
			break;
		case R.id.btn_check:
			AlertDialog.Builder builder2 = new AlertDialog.Builder(this);
			builder2.setTitle("提示");
			View view2 = View.inflate(this, R.layout.check, null);
			final EditText n = (EditText) view2.findViewById(R.id.et_check);
			view2.findViewById(R.id.btn_check).setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					if(TextUtils.isEmpty(n.getText().toString().trim())){
						dialog2.dismiss();
					}else{
						ArrayList<EmployeeBean> datass =  (ArrayList<EmployeeBean>) dao.quary(n.getText().toString().trim());
						if(datass.size() != 0){
							Intent intent = new Intent(MainActivity.this, ResultActivity.class);
							intent.putExtra("list", datass);
							MainActivity.this.startActivity(intent);
						}
						dialog2.dismiss();
					}
				}
			});
			builder2.setView(view2);
			builder2.setCancelable(false);
			dialog2 = builder2.create();
			dialog2.show();
			break;
		case R.id.btn_delete:
			List<EmployeeBean> data = adapter.getData();
			boolean check = false;
			for (EmployeeBean employeeBean : data) {
				if(employeeBean.isCheck()){
					dao.delete(employeeBean.getId());
					check = true;
				}
			}
			if(!check){
				Toast.makeText(getApplicationContext(), "您未选择", 0).show();
				return;
			}
			load();
			break;
		case R.id.btn_select:
			List<EmployeeBean> data2 = adapter.getData();
			if(btn_select.getText().toString().equals("全选")){
				btn_select.setText("取消");
				for (EmployeeBean employeeBean : data2) {
					employeeBean.setCheck(true);
					adapter.notifyDataSetChanged();
				}
			}else{
				btn_select.setText("全选");
				for (EmployeeBean employeeBean : data2) {
					employeeBean.setCheck(false);
					adapter.notifyDataSetChanged();
				}
			}
			break;
		case R.id.btn_calc:
			ArrayList<EmployeeBean> listCalc = (ArrayList<EmployeeBean>) adapter.getData();
			ArrayList<EmployeeBean> listIntent = new ArrayList<EmployeeBean>();
			for (EmployeeBean employeeBean : listCalc) {
				if(employeeBean.isCheck()){
					listIntent.add(employeeBean);
				}
			}
			if(listIntent.size() == 0){
				Toast.makeText(getApplicationContext(), "请选择再计算", 0).show();
				return;
			}
			Intent intent = new Intent(MainActivity.this, CalcActivity.class);
			intent.putExtra("data", listIntent);
			MainActivity.this.startActivity(intent);
			break;
		default:
			break;
		}
		
	}

	@Override
	public int getLayoutRsid() {
		return R.layout.activity_main;
	}

	@Override
	public View createSuccessView() {
		listView = new ListView(this);
		listView.setAdapter(adapter);
		listView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				final EmployeeBean b = (EmployeeBean) adapter.getItem(arg2);
				AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
				builder.setTitle("修改");
				View view = View.inflate(MainActivity.this, R.layout.add, null);
				view.findViewById(R.id.lll).setVisibility(View.GONE);
				 final EditText tv_name = (EditText) view.findViewById(R.id.et_name);
				 final EditText tv_salary = (EditText) view.findViewById(R.id.et_salary);
				tv_name.setText(b.getName());
				tv_salary.setText(b.getSalary());
				final String name = tv_name.getText().toString().trim();
				final String sal = tv_salary.getText().toString().trim();
				view.findViewById(R.id.btn_yes).setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						if(!TextUtils.isEmpty(name) &&
								!TextUtils.isEmpty(sal)){
							dao.updata(tv_name.getText().toString().trim(),b.getId(), 
									tv_salary.getText().toString().trim());
							load();
							dialog.dismiss();
						}
						
					}
				});
				view.findViewById(R.id.btn_cancel).setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						
						dialog.dismiss();
					}
				});
				builder.setView(view);
				builder.setCancelable(false);
				dialog = builder.create();
				dialog.show();
				return false;
			}
		});
		return listView;
	}

	@Override
	public LoadResult load() {
		final List<EmployeeBean> list = dao.quary();
		if(list.size() == 0){
			return LoadResult.empty;
		}
		runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				adapter.setDatas(list);
			}
		});
		return LoadResult.success;
	}
	@Override
	public void initView() {
		super.initView();
		findViewById(R.id.btn_add).setOnClickListener(this);
		findViewById(R.id.btn_check).setOnClickListener(this);
		findViewById(R.id.btn_delete).setOnClickListener(this);
		btn_select = (Button) findViewById(R.id.btn_select);
		btn_select.setOnClickListener(this);
		btn_calc = (Button) findViewById(R.id.btn_calc);
		btn_calc.setOnClickListener(this);
		dao = new EmployeeDao(this);
	}

}
