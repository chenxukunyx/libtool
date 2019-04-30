package com.shentu.lib_image.loader;

import android.app.Activity;
import android.content.Context;
import android.widget.ImageView;

import com.shentu.lib_image.options.DisplayOption;
import com.shentu.lib_image.options.InitOption;

import java.io.File;


public abstract class IImageLoader {

    public abstract void display(ImageView imageView, String url);

    public abstract void display(ImageView imageView, String url, DisplayOption op);

    public abstract void display(ImageView imageView, String url, String op);

    public abstract void display(Activity activity, ImageView imageView, String url);

    public abstract void display(Activity activity, ImageView imageView, String url, DisplayOption op);

    public abstract void display(Activity activity, ImageView imageView, String url, String op);

    public abstract void display(ImageView imageView, int resId);

    public abstract void display(ImageView imageView, int resId, DisplayOption op);

    public abstract void display(ImageView imageView, int resId, String op);

    public abstract void display(Activity activity, ImageView imageView, int resId);

    public abstract void display(Activity activity, ImageView imageView, int resId, DisplayOption op);

    public abstract void display(Activity activity, ImageView imageView, int resId, String op);

    public abstract void display(ImageView imageView, File file);

    public abstract void display(ImageView imageView, File file, DisplayOption op);

    public abstract void display(ImageView imageView, File file, String op);

    public abstract void display(Activity activity, ImageView imageView, File file);

    public abstract void display(Activity activity, ImageView imageView, File file, DisplayOption op);

    public abstract void display(Activity activity, ImageView imageView, File file, String op);

    public abstract void setDefaultOption(DisplayOption option);

    public abstract void startGif(ImageView imageView);

    public abstract void stopGif(ImageView imageView);

    public abstract void recycleGif(ImageView imageView);

    public abstract void cancel(ImageView imageView);

    public abstract void pauseRequest();

    public abstract void resumeRequest();

    public abstract void clearMemoryCache();

    public abstract void clearDiskCache();

    public abstract void onTrimMemory(int level);

    public abstract void onLowMemory();

    public abstract void init(Context context, InitOption option);

    public abstract void release();
}
