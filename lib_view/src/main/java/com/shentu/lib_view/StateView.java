package com.shentu.lib_view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shentu.lib_tools.NetworkUtils;
import com.shentu.lib_tools.ToolsKernel;

import androidx.annotation.ColorRes;
import androidx.annotation.Nullable;

/**
 * Created with Android Studio
 *
 * @author: chenxukun
 * @date: 2019/3/26
 * @time: 3:50 PM
 * @fuction:
 */
public class StateView extends LinearLayout {

    private LinearLayout mLlLoadFailed, mLlEmpty;
    private TextView mTvReload;
    private OnReloadListener mOnReloadListener;

    public StateView(Context context) {
        this(context, null);
    }

    public StateView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StateView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
        hide();
    }

    private void init(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.state_view, this, true);
        mLlLoadFailed = view.findViewById(R.id.ll_load_failed);
        mLlEmpty = view.findViewById(R.id.ll_empty);
        mTvReload = view.findViewById(R.id.tv_reload);

        mTvReload.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnReloadListener != null) {
                    hide();
                    mOnReloadListener.onReload();
                }
            }
        });
    }

    public void loadFailed(OnReloadListener listener) {
        if (NetworkUtils.isNetworkConnected(ToolsKernel.getInstance.getContext())) {
            loadFailed("点击重新加载", R.color.color_4093f8, listener);
        } else {
            networkUnAvaliable();
        }
    }

    public void networkUnAvaliable() {
        loadFailed("请检查是否已连接网络！", R.color.color_4093f8, null);
    }

    public void notThisInfomation() {
        loadFailed("找不到信息，文章可能已被删除", R.color.color_4093f8, null);
    }

    private void loadFailed(String msg, @ColorRes int color, OnReloadListener listener) {
        setVisibility(VISIBLE);
        mLlLoadFailed.setVisibility(VISIBLE);
        mLlEmpty.setVisibility(GONE);
        mTvReload.setText(msg);
        mTvReload.setTextColor(getResources().getColor(color));
        mOnReloadListener = listener;
    }

    public void empty() {
        setVisibility(VISIBLE);
        mLlLoadFailed.setVisibility(GONE);
        mLlEmpty.setVisibility(VISIBLE);
    }

    public void hide() {
        setVisibility(GONE);
    }

    public interface OnReloadListener {
        void onReload();
    }
}
