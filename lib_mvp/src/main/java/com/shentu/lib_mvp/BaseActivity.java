package com.shentu.lib_mvp;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.miracle.fast_tool.BasePermissionActivity;
import com.shentu.lib_mvp.EventBus.EventBusUtil;
import com.shentu.lib_mvp.EventBus.EventMessage;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import androidx.annotation.Nullable;

/**
 * Created with Android Studio
 *
 * @author: chenxukun
 * @date: 2019/2/26
 * @time: 1:12 PM
 * @fuction:
 */
public abstract class BaseActivity extends BasePermissionActivity {

    private InputMethodManager imm;

    public LoadingProgress mLoadingProgress;

    public static final int STATE_REFRESH = 1;
    public static final int STATE_LOADMORE = 2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        mLoadingProgress = new LoadingProgress(this);
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (isRegisterEventBus()) {
            EventBusUtil.register(this);
        }
    }

    //是否开启沉浸式状态栏  默认开启
    protected boolean isWindowDrawsSystemBarBackgrounds() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return false;
        }
        return true;
    }

    /**
     * 是否启用状态栏亮模式， 默认不启用
     * @return
     */
    protected boolean statusBarLightMode() {
        return false;
    }

    /**
     * 是否注册eventbus
     * @return
     */
    public boolean isRegisterEventBus() {
        return false;
    }

    protected abstract int getLayoutId();

    public void initView() {

    }

    @Override
    protected void onDestroy() {
        if (isRegisterEventBus()) {
            EventBusUtil.unregister(this);
        }
        super.onDestroy();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceiveEvent(EventMessage message) {

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN){
            View view = getCurrentFocus();
            if (view == null) {
                return super.dispatchTouchEvent(ev);
            }
            if (shouldHideInput(view, ev)) {
                if (hideInputMethod(view)) {
                    return super.dispatchTouchEvent(ev);
                }
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 隐藏软键盘
     * @param view
     * @return
     */
    protected boolean hideInputMethod(View view) {
        return imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * 点击非Edittext区域时隐藏软键盘
     * @param view
     * @param ev
     * @return
     */
    private boolean shouldHideInput(View view, MotionEvent ev) {
        if (view instanceof EditText) {
            int[] leftTop = {0, 0};
            view.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + view.getHeight();
            int right = left + view.getWidth();
            if (ev.getX() > left && ev.getX() < right && ev.getY() > top && ev.getY() < bottom) {
//                ((EditText)view).setCursorVisible(true);
                return false;
            } else {
//                ((EditText)view).setCursorVisible(false);
                return true;
            }
        }
        return false;
    }
}
