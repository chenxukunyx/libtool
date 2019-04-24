package com.shentu.lib_image.options;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.View;

/**
 * Created with Android Studio
 *
 * @author: chenxukun
 * @date: 2019/2/26
 * @time: 3:52 PM
 * @fuction:
 */
public class DisplayOption {

    /**
     * 圆角位置参数定义
     */
    public static final int RadiusAll = 0x1111;
    public static final int RadiusTop = 0x1100;
    public static final int RadiusBottom = 0x0011;
    public static final int RadiusRight = 0x0110;
    public static final int RadiusLeft = 0x1001;
    public static final int RadiusTopLeft = 0x1000;
    public static final int RadiusTopRight = 0x100;
    public static final int RadiusBottomRight = 0x10;
    public static final int RadiusBottomLeft = 0x1;
    public static final int RadiusOtherTopLeft = RadiusAll ^ RadiusTopLeft;
    public static final int RadiusOtherTopRight = RadiusAll ^ RadiusTopRight;
    public static final int RadiusOtherBottomLeft = RadiusAll ^ RadiusBottomLeft;
    public static final int RadiusOtherBottomRight = RadiusAll ^ RadiusBottomRight;
    public static final int RadiusDiagonalTopLeft = RadiusTopLeft | RadiusBottomRight;
    public static final int RadiusDiagoalTopRight = RadiusTopRight | RadiusBottomLeft;

    /**
     * 是否磁盘缓存
     */
    boolean mCacheOnDisc = true;
    /**
     * 是否内存缓存
     */
    boolean mCacheInMemory = true;
    /**
     * bitmap格式设置，RGB8888、RGB656
     */
    Bitmap.Config mBitmapConfig = Bitmap.Config.RGB_565;
    /**
     * 默认背景色，当RGB_565模式的时候，因为没有透明度，透明部分填充这个背景色
     */
    int mBackgroundColor = Color.WHITE;

    /**
     * 设置圆角
     */
    int mRadius = 0;
    /**
     * 设置圆角位置，0x1111，顺时针方向对应左上，右上，右下，左下
     * 参考上面参数定义
     */
    int mRadiusPosition = RadiusTopLeft | RadiusTopRight | RadiusBottomRight | RadiusBottomLeft;
    /**
     * 加载中图片resID
     */
    int mLoadingImageResId = -1;
    /**
     * 加载中图片drawable对象
     */
    Drawable mLoadingImage;
    /**
     * 加载失败图片resID
     */
    int mErrorImageResId = -1;
    /**
     * 加载失败图片drawable对象
     */
    Drawable mErrorImage;
    /**
     * 图片在View中的适配
     */
    FitOption mFitOption = FitOption.CenterCrop;
    /**
     * 图片显示特效
     */
    int mEffectOption = EffectOption.None;
    /**
     * 用于Mask effect的mask image resource id
     */
    int mMaskImageResId = -1;
    /**
     * 设置显示缩略图
     */
    float mThumbnail = 0.0f;
    /**
     * 设置图片加载动画
     */
    int mAnimateResId = 0;
    /**
     * Gif动画循环次数
     */
    int mGifLoopCount = 0;
    /**
     * 是否是Gif格式
     */
    boolean mIsGif = false;
    /**
     * 修改image显示尺寸
     */
    int mOverrideWidth = 0;
    int mOverrideHeight = 0;
    /**
     * 预加载参数
     */
    boolean mPreload = false;
    /**
     * 将bitmap输出到这个view的背景上
     */
    View mEffectOutputView;
    /**
     * 高斯模糊程度值
     */
    int mBlurRadius = 25;
    /**
     * 高斯冲采样比例
     */
    int mBlurSampling = 8;

    public DisplayOption cacheOnDisc(boolean setting) {
        mCacheOnDisc = setting;
        return this;
    }

    public DisplayOption cacheInMemory(boolean setting) {
        mCacheInMemory = setting;
        return this;
    }

    public DisplayOption bitmapConfig(Bitmap.Config config) {
        mBitmapConfig = config;
        return this;
    }

    public DisplayOption backgroundColor(int color) {
        mBackgroundColor = color;
        return this;
    }

    public DisplayOption radius(int value) {
        mRadius = value;
        return this;
    }

    public DisplayOption radiusPosition(int position) {
        mRadiusPosition = position;
        return this;
    }

    public DisplayOption loadingImageRes(int id) {
        mLoadingImageResId = id;
        return this;
    }

    public DisplayOption errorImageRes(int id) {
        mErrorImageResId = id;
        return this;
    }

    public DisplayOption loadingImage(Drawable drawable) {
        mLoadingImage = drawable;
        return this;
    }

    public DisplayOption errorImage(Drawable drawable) {
        mErrorImage = drawable;
        return this;
    }

    public DisplayOption fitOption(FitOption option) {
        mFitOption = option;
        return this;
    }

    public DisplayOption effectOption(int option) {
        mEffectOption = option;
        return this;
    }

    public DisplayOption maskImageResId(int resId) {
        mMaskImageResId = resId;
        return this;
    }

    public DisplayOption thumbnail(float value) {
        mThumbnail = value;
        return this;
    }

    public DisplayOption animateResId(int resId) {
        mAnimateResId = resId;
        return this;
    }

    public DisplayOption gifLoopCount(int count) {
        mGifLoopCount = count;
        return this;
    }

    public DisplayOption asGif() {
        mIsGif = true;
        return this;
    }

    public DisplayOption asBitmap() {
        mIsGif = false;
        return this;
    }

    public DisplayOption override(int width, int height) {
        mOverrideWidth = width;
        mOverrideHeight = height;
        return this;
    }

    public DisplayOption preload(boolean setting) {
        mPreload = setting;
        return this;
    }

    public DisplayOption outputView(View view) {
        mEffectOutputView = view;
        return this;
    }

    public DisplayOption blurRadius(int radius) {
        mBlurRadius = radius;
        return this;
    }

    public DisplayOption blurSampling(int sampling) {
        mBlurSampling = sampling;
        return this;
    }

    public boolean getCacheOnDisc() {
        return mCacheOnDisc;
    }

    public boolean getCacheInMemory() {
        return mCacheInMemory;
    }

    public Bitmap.Config getBitmapConfig() {
        return mBitmapConfig;
    }

    public int getBackgroundColor() {
        return mBackgroundColor;
    }

    public int getRadius() {
        return mRadius;
    }

    public int getRadiusPosition() {
        return mRadiusPosition;
    }

    public int getLoadingImageResId() {
        return mLoadingImageResId;
    }

    public int getErrorImageResId() {
        return mErrorImageResId;
    }

    public Drawable getLoadingImage() {
        return mLoadingImage;
    }

    public Drawable getErrorImage() {
        return mErrorImage;
    }

    public FitOption getFitOption() {
        return mFitOption;
    }

    public int getEffectOption() {
        return mEffectOption;
    }

    public int getMaskImageResId() {
        return mMaskImageResId;
    }

    public float getThumbnail() {
        return mThumbnail;
    }

    public int getAnimateResId() {
        return mAnimateResId;
    }

    public int getGifLoopCount() {
        return mGifLoopCount;
    }

    public boolean isGif() {
        return mIsGif;
    }

    public int getOverrideWidth() {
        return mOverrideWidth;
    }

    public int getOverrideHeight() {
        return mOverrideHeight;
    }

    public boolean isPreload() {
        return mPreload;
    }

    public View getEffectOutputView() {
        return mEffectOutputView;
    }

    public int getBlurRadius() {
        return mBlurRadius;
    }

    public int getBlurSampling() {
        return mBlurSampling;
    }

    /**
     * 图片在View中的适配参数
     */
    public static enum FitOption {
        None,
        FitCenter,
        CenterCrop,
        CircleCrop,
        CenterInside;

        private FitOption() {
        }
    }

    /**
     * 图片在View中的适配参数
     */
    public class EffectOption {
        public static final int None = 0;
        public static final int GrayScale = (1 << 0);
        public static final int ColorFilter = (1 << 1);
        public static final int Mask = (1 << 2);
        public static final int Blur = (1 << 3);

        public EffectOption() {
        }
    }
}
