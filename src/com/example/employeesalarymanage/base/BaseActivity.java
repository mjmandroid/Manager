package com.example.employeesalarymanage.base;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.example.employeesalarymanage.R;
import com.example.employeesalarymanage.view.LoadingPage;

/**
 * Created by Administrator on 2016/5/3.
 */
public abstract class BaseActivity extends Activity implements View.OnClickListener {
    public static List<BaseActivity> mActivities = new ArrayList<BaseActivity>();
    private LoadingPage loadingPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        synchronized (mActivities){
            mActivities.add(this);
        }
        init();
        initView();
        initData();
    }

    private void init() {
            loadingPage = new LoadingPage(this) {
            @Override
            public View createSuccessView() {
                return BaseActivity.this.createSuccessView();
            }

            @Override
            protected LoadResult load() {
                return BaseActivity.this.load();
            }
        };
        setContentView(getLayoutRsid());
        LinearLayout ll = (LinearLayout) findViewById(R.id.ll);
        ll.addView(loadingPage, LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
    }
    public abstract int getLayoutRsid();
    public void show(){
        if(loadingPage!=null){
            loadingPage.show();
        }
    }
    public void killAllActiitys(){
        List<BaseActivity> copy;
        synchronized (mActivities) {
            copy = new LinkedList<BaseActivity>(mActivities);
        }
        for (BaseActivity activity : copy) {
            activity.finish();
        }
        // 杀死当前的进程
        android.os.Process.killProcess(android.os.Process.myPid());
    }
    public void initData() {
    }

    public void initView() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        synchronized (mActivities) {
            mActivities.remove(this);
        }
    }
    public LoadingPage.LoadResult checkData(String json){
        if(json == null){
            return LoadingPage.LoadResult.error;
        }else{
            return LoadingPage.LoadResult.success;
        }
    }

    public abstract  View createSuccessView();
    public abstract LoadingPage.LoadResult load();

   
}
