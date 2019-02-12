package com.miracle.fast_tool;

import android.app.Application;
import android.content.Context;
import com.miracle.fast_tool.crashhandler.DefaultCrashHandler;
import com.miracle.fast_tool.crashhandler.ICrashHandler;

public class BaseApplication extends Application {

    private static Context context;
    private ICrashHandler crashHandler;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        crashHandler = getCrashHandler();
        if (crashHandler != null)
            crashHandler.init(this);
        else
            throw new NullPointerException("if you do not define crashhandler, use super.getCrashHandler...");
    }

    protected ICrashHandler getCrashHandler() {
        return new DefaultCrashHandler();
    }

    public static Context getContext() {
        return context;
    }
}
