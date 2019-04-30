package com.miracle.fast_tool.crashhandler;

import android.content.Context;

import java.io.File;

public interface ICrashHandler {

    void init(Context context);
    void dumpExceptionToSdcard(Throwable e);
    void dumpExceptionToCloud(File file);
}
