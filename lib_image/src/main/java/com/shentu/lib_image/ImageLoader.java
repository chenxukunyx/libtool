package com.shentu.lib_image;

import android.content.Context;

import com.shentu.lib_image.loader.GlideLoader;
import com.shentu.lib_image.loader.IImageLoader;
import com.shentu.lib_image.options.InitOption;

/**
 * Created with Android Studio
 *
 * @author: chenxukun
 * @date: 2019/2/26
 * @time: 3:55 PM
 * @fuction:
 */
public class ImageLoader {

    private static IImageLoader mLoader;

    public static void init(Context context, InitOption option) {
        init(context, GlideLoader.class, option);
    }

    public static void init(Context context, Class<? extends IImageLoader> clazz, InitOption option) {
        try {
            mLoader = clazz.newInstance();
            mLoader.init(context, option);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

    public static IImageLoader get() {
        if (mLoader == null)
            throw new IllegalStateException("imageloader cannot be null, please init first");
        return mLoader;
    }
}
