package com.shentu.lib_view.banner;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * Created with Android Studio
 *
 * @author: chenxukun
 * @date: 2019/2/25
 * @time: 9:46 AM
 * @fuction:
 */
public class ScreenUtils {

    public static DisplayMetrics getDisplayMetrics(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager)context.getApplicationContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics;
    }

    public static String getThumbnailUrl(Context context, String url) {
        int imageWidth = getDisplayMetrics(context).widthPixels;
        int imageHeight = getDisplayMetrics(context).heightPixels;
        return getThumbnailUrl(context, url, imageWidth, imageHeight);
    }

    public static String getThumbnailUrl(Context context, String url, int width, int height) {
//        if (width == 0 || height == 0) {
////            return url +
////        }
        //TODO
        return "";
    }
}
