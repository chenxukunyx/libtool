package com.miracle.fast_tool.crashhandler;

import android.content.Context;

public interface ICrashHandler {

    void init(Context context);
    void dumpExceptionToSdcard(Throwable e);
    void dumpExceptionToCloud(Throwable e);
}
