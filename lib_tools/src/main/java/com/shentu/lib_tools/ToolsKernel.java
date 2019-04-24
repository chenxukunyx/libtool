package com.shentu.lib_tools;

import android.content.Context;

/**
 * Created with Android Studio
 *
 * @author: chenxukun
 * @date: 2019/2/25
 * @time: 2:31 PM
 * @fuction:
 */
public enum  ToolsKernel {
    getInstance;

    private Context mContext;

    public void init(Context context) {
        mContext = context.getApplicationContext();
    }

    public Context getContext() {
        if (mContext == null) throw new IllegalArgumentException("ToolsKernel context == null");
        return mContext;
    }
}
