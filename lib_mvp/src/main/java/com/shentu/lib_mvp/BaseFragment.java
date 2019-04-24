package com.shentu.lib_mvp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.miracle.fast_tool.BasePermissionFragment;
import com.shentu.lib_mvp.EventBus.EventBusUtil;
import com.shentu.lib_mvp.EventBus.EventMessage;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Created with Android Studio
 *
 * @author: chenxukun
 * @date: 2019/2/26
 * @time: 1:26 PM
 * @fuction:
 */
public abstract class BaseFragment extends BasePermissionFragment {

    public static final int STATE_REFRESH = 1;
    public static final int STATE_LOADMORE = 2;

    public boolean isRegisterEventBus() {
        return false;
    }

    protected BaseActivity mActivity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        return view;
    }

    protected abstract int getLayoutId();

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mActivity = (BaseActivity) getActivity();
        if (isRegisterEventBus()) {
            EventBusUtil.register(this);
        }
        initView(view);
    }

    protected void initView(View view) {

    }

    @Override
    public void onDestroyView() {
        if (isRegisterEventBus()) {
            EventBusUtil.unregister(this);
        }
        super.onDestroyView();
    }

    protected boolean isHideStatusBar() {
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceiveEvent(EventMessage message) {

    }
}
