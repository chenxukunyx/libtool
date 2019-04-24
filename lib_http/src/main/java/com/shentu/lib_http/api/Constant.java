package com.shentu.lib_http.api;

import com.shentu.lib_tools.ToolsKernel;

import java.io.File;

/**
 * Created with Android Studio
 *
 * @author: chenxukun
 * @date: 2019/2/25
 * @time: 3:55 PM
 * @fuction:
 */
public class Constant {

    public static final String HTTP_TAG = "HttpApi";

    public static final File CACHE_FILE = new File(ToolsKernel.getInstance.getContext().getExternalCacheDir().getPath() + "/httpcache/");
    public static final int CACHE_SIZE = 100 * 1024 * 1024;//100M
    public static final int CONNECT_TIMEOUT = 10;
    public static final int READ_TIMEOUT = 10;
    public static final int WRITE_TIMEOUT = 10;
}
