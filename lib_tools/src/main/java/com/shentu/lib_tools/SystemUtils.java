package com.shentu.lib_tools;

import android.content.Context;
import android.content.pm.PackageManager;

/**
 * Created with Android Studio
 *
 * @author: chenxukun
 * @date: 2019/2/25
 * @time: 2:35 PM
 * @fuction:
 */
public class SystemUtils {


    /**
     * 获取当前进程的软件版本号
     * @return
     */
    public static String getVersionName() {
        return getVersionName(ToolsKernel.getInstance.getContext(), ToolsKernel.getInstance.getContext().getPackageName());
    }

    /**
     * 获取制定包名app的软件版本号
     *
     * @param context     : 当前进程application context
     * @param packageName : 当前进程application context
     * @return
     */
    public static String getVersionName(Context context, String packageName) {
        String versionName = "";
        try {
            versionName = context.getPackageManager().getPackageInfo(
                    packageName, 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName;
    }

    /**
     * 获取当前进程软件版本code
     *
     * @param context : application context
     * @return
     */
    public static int getVersionCode(Context context) {
        return getVersionCode(ToolsKernel.getInstance.getContext(), ToolsKernel.getInstance.getContext().getPackageName());
    }

    /**
     * 获取指定包名应用软件版本号
     *
     * @param context     : application context
     * @param packageName : 包名
     * @return
     */
    public static int getVersionCode(Context context, String packageName) {
        int versionCode = 0;
        if (packageName != null) {
            try {
                versionCode = context.getPackageManager().getPackageInfo(
                        packageName, 0).versionCode;
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
        return versionCode;
    }


    /**
     * dp转px
     *
     * @param cCtx    : application context
     * @param dpValue : dip value
     * @return
     */
    public static int dip2px(Context cCtx, float dpValue) {
        float scale = cCtx.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * px转dp
     *
     * @param cCtx    : application context
     * @param pxValue : px value
     * @return
     */
    public static int px2dip(Context cCtx, float pxValue) {
        float scale = cCtx.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
}
