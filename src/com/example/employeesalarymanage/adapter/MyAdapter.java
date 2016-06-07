package com.example.employeesalarymanage.adapter;

import java.util.List;

import android.content.Context;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

import com.example.employeesalarymanage.R;
import com.example.employeesalarymanage.domain.EmployeeBean;

public class MyAdapter extends AbsBaseAdapter<EmployeeBean>{

	public MyAdapter(Context context) {
		super(context, R.layout.list_item_layout);
		
	}

	@Override
	public void bindDatas(ViewHolder viewHolder,
			final EmployeeBean data) {
		TextView id = (TextView) viewHolder.getView(R.id.id);
		TextView name = (TextView) viewHolder.getView(R.id.name);
		final CheckBox cb = (CheckBox) viewHolder.getView(R.id.cb);
		TextView salary = (TextView) viewHolder.getView(R.id.salary);
		id.setText(data.getId());
		name.setText(data.getName());
		salary.setText(data.getSalary());
		cb.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				data.setCheck(isChecked);
			}
		});
		cb.setChecked(data.isCheck());
	}
	
}
