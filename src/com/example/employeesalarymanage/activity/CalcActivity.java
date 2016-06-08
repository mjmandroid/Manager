package com.example.employeesalarymanage.activity;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.employeesalarymanage.R;
import com.example.employeesalarymanage.domain.EmployeeBean;

public class CalcActivity extends Activity {
	private TextView tvAvrMoney;
	private TextView tv_allMoney;
	private ArrayList<EmployeeBean> list;

	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_calc);
		Intent intent = getIntent();
		if (intent != null) {
			list = (ArrayList<EmployeeBean>) intent
					.getSerializableExtra("data");
		}
		initView();
	}

	private void initView() {
		tvAvrMoney = (TextView) findViewById(R.id.tv_avrmoney);
		tv_allMoney = (TextView) findViewById(R.id.tv_allmoney);

	}

	public void avrMoney(View veiw) {
		if(list != null){
			int size = list.size();
			double m = 0;
			for (EmployeeBean bean : list) {
				String money = bean.getSalary();
				try {
					Double salary = Double.parseDouble(money);
					m += salary;
				} catch (NumberFormatException e) {
					e.printStackTrace();
					Toast.makeText(getApplicationContext(), "输入的工资格式有误，请检查！", 0).show();
				}
			}
			double avr = m / size;
			tvAvrMoney.setText("平均薪水:"+avr);
		}
	}

	public void allMoney(View veiw) {
		if(list != null){
			double m = 0;
			for (EmployeeBean bean : list) {
				String money = bean.getSalary();
				try {
					Double salary = Double.parseDouble(money);
					m += salary;
				} catch (NumberFormatException e) {
					e.printStackTrace();
					Toast.makeText(getApplicationContext(), "输入的工资格式有误，请检查！", 0).show();
				}
			}
			tv_allMoney.setText("总金额:"+m);
		}
	}
}
