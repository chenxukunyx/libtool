package com.shentu.lib_image.options;


public class InitOption {

    /**
     * 默认disc cache大小
     */
    static int mDefaultDiscCacheSize = 64;

    /**
     * memmory cache 大小，单位MegaBytes
     */
    int mMemmoryCacheSize;
    /**
     * disc cache 大小，单位MegaBytes
     */
    int mDiscCacheSize = mDefaultDiscCacheSize;
    /**
     * 网络加载线程池线程个数
     */
    int mThreadPoolSize;
    /**
     * 默认displayOption
     */
    DisplayOptionFactory mOptionFactory;

    public InitOption memmoryCache(int size) {
        mMemmoryCacheSize = size;
        return this;
    }

    public InitOption discCache(int size) {
        mDiscCacheSize = size;
        return this;
    }

    public InitOption threadPoolSize(int size) {
        mThreadPoolSize = size;
        return this;
    }

    public InitOption optionFactory(DisplayOptionFactory option) {
        mOptionFactory = option;
        return this;
    }

    public int getmMemmoryCacheSize() {
        return mMemmoryCacheSize;
    }

    public int getmDiscCacheSize() {
        return mDiscCacheSize;
    }

    public int getmThreadPoolSize() {
        return getmThreadPoolSize();
    }

    public DisplayOptionFactory getOptionFactory() {
        return mOptionFactory;
    }

    public static void changeDefaultDiscCacheSize(int size) {
        mDefaultDiscCacheSize = size;
    }

    public static int getmDefaultDiscCacheSize() {
        return mDefaultDiscCacheSize;
    }
}
