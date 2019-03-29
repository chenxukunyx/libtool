package com.miracle.fast_tool.tools;

import android.content.Context;

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
