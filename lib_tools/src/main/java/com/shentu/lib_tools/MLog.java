package com.shentu.lib_tools;

import android.util.Log;

/**
 * Created with Android Studio
 *
 * @author: chenxukun
 * @date: 2019/2/26
 * @time: 2:02 PM
 * @fuction:
 */
public class MLog {

    private static final String TAG = "miracle_log";

    public static void v(Object msg) {
        v(null, msg);
    }

    public static void v(String tag, Object msg){
        if (tag == null) {
            tag = TAG;
        }
        Log.v(tag, "[Verbose]: " + msg.toString());
    }

    public static void d(Object msg) {
        d(null, msg);
    }

    public static void d(String tag, Object msg){
        if (tag == null) {
            tag = TAG;
        }
        Log.d(tag, "[Debug]: " + msg.toString());
    }

    public static void i(Object msg) {
        i(null, msg);
    }

    public static void i(String tag, Object msg){
        if (tag == null) {
            tag = TAG;
        }
        Log.i(tag, "[Info]: " + msg.toString());
    }

    public static void w(Object msg) {
        w(null, msg);
    }

    public static void w(String tag, Object msg){
        if (tag == null) {
            tag = TAG;
        }
        Log.w(tag, "[Warn]: " + msg.toString());
    }

    public static void e(Object msg) {
        e(null, msg);
    }

    public static void e(String tag, Object msg){
        if (tag == null) {
            tag = TAG;
        }
        Log.e(tag, "[Error]: " + msg.toString());
    }
}
