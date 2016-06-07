package com.example.employeesalarymanage.view;

import com.example.employeesalarymanage.R;
import com.example.employeesalarymanage.manger.ThreadManager;

import android.app.Activity;
import android.content.Context;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;


/**
 * Created by Administrator on 2016/5/3.
 */
public abstract class LoadingPage extends FrameLayout {
    public static final int STATE_UNKOWN = 0;
    public static final int STATE_LOADING = 1;
    public static final int STATE_ERROR = 2;
    public static final int STATE_EMPTY = 3;
    public static final int STATE_SUCCESS = 4;
    public int state = STATE_UNKOWN;

    private View loadingView;// 加载中的界面
    private View errorView;// 错误界面
    private View emptyView;// 空界面
    private View successView;// 加载成功的界面
    private Activity context;
    public LoadingPage(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public LoadingPage(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LoadingPage(Context context) {
        super(context);
        this.context = (Activity) context;
        init();
    }

    private void init() {
        loadingView = createLoadingView(); // 创建了加载中的界面
        if (loadingView != null) {
            this.addView(loadingView, new FrameLayout.LayoutParams(
                    LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        }
        errorView = createErrorView(); // 加载错误界面
        if (errorView != null) {
            this.addView(errorView, new FrameLayout.LayoutParams(
                    LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        }
        emptyView = createEmptyView(); // 加载空的界面
        if (emptyView != null) {
            this.addView(emptyView, new FrameLayout.LayoutParams(
                    LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        }
        showPage();// 根据不同的状态显示不同的界面
    }
    public enum LoadResult{
        error(2),empty(3),success(4);
        int value;

        LoadResult(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
    private void showPage() {
        if (loadingView != null) {
            loadingView.setVisibility(state == STATE_UNKOWN
                    || state == STATE_LOADING ? View.VISIBLE : View.INVISIBLE);
        }
        if (errorView != null) {
            errorView.setVisibility(state == STATE_ERROR ? View.VISIBLE
                    : View.INVISIBLE);
        }
        if (emptyView != null) {
            emptyView.setVisibility(state == STATE_EMPTY ? View.VISIBLE
                    : View.INVISIBLE);
        }
        if (state == STATE_SUCCESS) {
            if (successView == null) {
                successView = createSuccessView();
                this.addView(successView, new FrameLayout.LayoutParams(
                        LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
            }
            successView.setVisibility(View.VISIBLE);
        } else {
            if (successView != null) {
                successView.setVisibility(View.INVISIBLE);
            }
        }
    }

    private View createEmptyView() {
        return View.inflate(context, R.layout.loadpage_empty,null);
    }

    private View createErrorView() {
        View view = View.inflate(context,R.layout.loadpage_error,null);
        Button page_bt = (Button) view.findViewById(R.id.page_bt);
        page_bt.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                show();
            }
        });
        return view;
    }

    public void show() {
        if (state == STATE_ERROR || state == STATE_EMPTY) {
            state = STATE_LOADING;
        }
        // 请求服务器 获取服务器上数据 进行判断
        // 请求服务器 返回一个结果
        ThreadManager.getInstance().createShortPool().execute(new Runnable() {

            @Override
            public void run() {
                SystemClock.sleep(2000);
                final LoadResult result = load();
                context.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        if (result != null) {
                            state = result.getValue();
                            showPage(); // 状态改变了,重新判断当前应该显示哪个界面
                        }
                    }
                });
            }
        });


        showPage();
    }

    private View createLoadingView() {
        return View.inflate(context,R.layout.loadpage_loading,null);
    }
    public abstract View createSuccessView();
    /**
     * 请求服务器
     *
     * @return
     */
    protected abstract LoadResult load();
}
