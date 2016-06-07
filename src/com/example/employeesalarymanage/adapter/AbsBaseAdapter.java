package com.example.employeesalarymanage.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.employeesalarymanage.domain.EmployeeBean;

/**
 * Created by Ken on 2015/11/13.
 */
public abstract class AbsBaseAdapter<T> extends BaseAdapter{
    private Context context;
    private List<T> datas;
    private int resid;

    public AbsBaseAdapter(Context context, int resid){
        this.context = context;
        this.resid = resid;
        datas = new ArrayList<T>();
    }

    public void setDatas(List<T> datas){
    	this.datas.clear();
        addDatas(datas);
        notifyDataSetChanged();
    }

    public void addDatas(List<T> datas){
        this.datas.addAll(datas);
        this.notifyDataSetChanged();
    }
    public void add(T data){
    	this.datas.add(data);
    	notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView != null){
            viewHolder = (ViewHolder) convertView.getTag();
        } else {
            convertView = LayoutInflater.from(context).inflate(resid, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }

        //从数据源中获得数据，依次放入ViewHodler中的控件里
        bindDatas(viewHolder, datas.get(position));
        return convertView;
    }

    public abstract void bindDatas(ViewHolder viewHolder, T data);

    /**
     * ViewHolder主要是用来缓存布局页面中的子控件，避免下一次使用时还需要findViewById
     */
    class ViewHolder{
        View layoutView;
        Map<Integer, View> mapCache = new HashMap<Integer, View>();

        public ViewHolder(View layoutView) {
            this.layoutView = layoutView;
        }

        public View getView(int id){
            View view = null;
            if(mapCache.containsKey(id)){
                view = mapCache.get(id);
            } else {
                view = layoutView.findViewById(id);
                mapCache.put(id, view);
            }
            return view;
        }
    }
    public List<T> getData(){
		return datas;
	}
}
