package com.shentu.lib_image.loader;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.MemoryCategory;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.ImageViewTarget;
import com.bumptech.glide.request.target.Target;
import com.shentu.lib_image.options.DisplayOption;
import com.shentu.lib_image.options.DisplayOptionFactory;
import com.shentu.lib_image.options.GlideOptionFactory;
import com.shentu.lib_image.options.InitOption;
import com.shentu.lib_image.transform.BlurTransformation;

import java.io.File;
import java.lang.ref.WeakReference;

import androidx.annotation.Nullable;


public class GlideLoader extends IImageLoader {

    /**
     * init参数
     */
    InitOption mInitOption;
    /**
     * application context
     */
    Context mCtx;
    /**
     * unique request listener
     */
    RequestListener mReqListener;

    /**
     * 特殊url的兼容
     *
     * param url
     * return
     */
    private String parseUrl(String url) {
        if (url == null) {
            url = "";
        }
        /**
         * imageloader的url 兼容
         */
        if (url.startsWith("assets://")) {
            return url.replace("assets://", "file:///android_asset/");
        } else if (url.startsWith("/")) {
            return "file://" + url;
        } else {
            return url;
        }
    }

    /**
     * 遍历工厂获取option
     *
     * param name
     * return
     */
    private DisplayOption getOptionInFactory(String name) {
        DisplayOption option = null;
        if (mInitOption != null && mInitOption.getOptionFactory() != null) {
            option = mInitOption.getOptionFactory().get(name);
        }
        if (option == null) {
            option = new DisplayOption();
        }
        return option;
    }

    /**
     * 加载网络图片
     *
     * param imageView
     * param url
     */
    @Override
    public void display(ImageView imageView, String url) {
        display(imageView, url, DisplayOptionFactory.getDefaultOptionName());
    }

    @Override
    public void display(ImageView imageView, String url, DisplayOption op) {
        display(Glide.with(mCtx), url, imageView, op);
    }

    @Override
    public void display(ImageView imageView, String url, String op) {
        display(imageView, url, getOptionInFactory(op));
    }

    @Override
    public void display(Activity activity, ImageView imageView, String url) {
        display(activity, imageView, url, DisplayOptionFactory.getDefaultOptionName());
    }

    @Override
    public void display(Activity activity, ImageView imageView, String url, DisplayOption op) {
        if (activity != null && !activity.isFinishing() && !activity.isDestroyed()) {
            display(Glide.with(activity), url, imageView, op);
        }
    }

    @Override
    public void display(Activity activity, ImageView imageView, String url, String op) {
        display(activity, imageView, url, getOptionInFactory(op));
    }

    /**
     * 加载资源图片
     *
     * param imageView
     * param resId
     */
    @Override
    public void display(ImageView imageView, int resId) {
        display(imageView, resId, DisplayOptionFactory.getDefaultOptionName());
    }

    @Override
    public void display(ImageView imageView, int resId, DisplayOption op) {
        RequestBuilder builder = op.isGif() ? Glide.with(mCtx).asGif().load(resId) : Glide.with(mCtx).load(resId);
        display(builder, imageView, op);
    }

    @Override
    public void display(ImageView imageView, int resId, String op) {
        display(imageView, resId, getOptionInFactory(op));
    }

    @Override
    public void display(Activity activity, ImageView imageView, int resId) {
        display(activity, imageView, resId, DisplayOptionFactory.getDefaultOptionName());
    }

    @Override
    public void display(Activity activity, ImageView imageView, int resId, DisplayOption op) {
        if (activity != null && !activity.isFinishing() && !activity.isDestroyed()) {
            RequestBuilder builder = op.isGif() ? Glide.with(activity).asGif().load(resId) : Glide.with(activity).load(resId);
            display(builder, imageView, op);
        }
    }

    @Override
    public void display(Activity activity, ImageView imageView, int resId, String op) {
        display(activity, imageView, resId, getOptionInFactory(op));
    }

    /**
     * 加载本地图片
     *
     * param imageView
     * param file
     */
    @Override
    public void display(ImageView imageView, File file) {
        display(imageView, file, DisplayOptionFactory.getDefaultOptionName());
    }

    @Override
    public void display(ImageView imageView, File file, DisplayOption op) {
        RequestBuilder builder = op.isGif() ? Glide.with(mCtx).asGif().load(file) : Glide.with(mCtx).load(file);
        display(builder, imageView, op);
    }

    @Override
    public void display(ImageView imageView, File file, String op) {
        display(imageView, file, getOptionInFactory(op));
    }

    @Override
    public void display(Activity activity, ImageView imageView, File file) {
        display(activity, imageView, file, DisplayOptionFactory.getDefaultOptionName());
    }

    @Override
    public void display(Activity activity, ImageView imageView, File file, DisplayOption op) {
        if (activity != null && !activity.isFinishing() && !activity.isDestroyed()) {
            RequestBuilder builder = op.isGif() ? Glide.with(activity).asGif().load(file) : Glide.with(activity).load(file);
            display(builder, imageView, op);
        }
    }

    @Override
    public void display(Activity activity, ImageView imageView, File file, String op) {
        display(activity, imageView, file, getOptionInFactory(op));
    }

    @Override
    public void setDefaultOption(DisplayOption option) {
        mInitOption.getOptionFactory().add(DisplayOptionFactory.getDefaultOptionName(), option);
    }

    @Override
    public void startGif(ImageView imageView) {
        if (imageView.getDrawable() != null && imageView.getDrawable() instanceof GifDrawable) {
            ((GifDrawable) imageView.getDrawable()).start();
        }
    }

    @Override
    public void stopGif(ImageView imageView) {
        if (imageView.getDrawable() != null && imageView.getDrawable() instanceof GifDrawable) {
            ((GifDrawable) imageView.getDrawable()).stop();
        }
    }

    @Override
    public void recycleGif(ImageView imageView) {
        if (imageView.getDrawable() != null && imageView.getDrawable() instanceof GifDrawable) {
            ((GifDrawable) imageView.getDrawable()).recycle();
        }
    }

    private void display(RequestManager manager, String url, ImageView imageView, DisplayOption op) {
        RequestBuilder builder;
        if (op.isGif()) {
            builder = manager.asGif();
        } else {
            builder = manager.asBitmap();
        }
        builder.load(parseUrl(url));
        display(builder, imageView, op);
    }

    private void display(RequestBuilder builder, ImageView imageView, final DisplayOption op) {

        /**
         * 对imageView使用弱引用，避免activity或者fragment长期不释放，导致imageView中的bitmap资源无法回收
         */
        WeakReference<ImageView> imageViewWeakReference = new WeakReference<>(imageView);
        if (imageViewWeakReference.get() == null) {
            return;
        }

        RequestOptions options = null;
        if (mInitOption != null && mInitOption.getOptionFactory() != null) {
            options = (RequestOptions) mInitOption.getOptionFactory().getTarget(op);
            if (options == null) {
                options = (RequestOptions) mInitOption.getOptionFactory().toTargetOptions(op);
            }
        }
        if (options != null) {
            builder.apply(options);
        }
        if (op.getThumbnail() != 0) {
            builder.thumbnail(op.getThumbnail());
        }

        if (op.isPreload()) {
            builder.preload();
        } else {
            if (op.getEffectOutputView() != null) {
                final DisplayOption oop = op;
                RequestListener requestListener = new RequestListener() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(final Object resource, Object model, Target target, DataSource dataSource, boolean isFirstResource) {
                        if (resource instanceof Bitmap) {
                            if (target instanceof ImageViewTarget) {
                                ImageViewTarget ivTarget = (ImageViewTarget)target;
                                final View view = ivTarget.getView();
                                if (oop.getEffectOutputView().getMeasuredHeight() == 0) {
                                    ViewTreeObserver viewTreeObserver = oop.getEffectOutputView().getViewTreeObserver();
                                    if (viewTreeObserver.isAlive()) {
                                        viewTreeObserver.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                                            @Override
                                            public boolean onPreDraw() {
                                                oop.getEffectOutputView().getViewTreeObserver().removeOnPreDrawListener(this);
                                                new BlurTransformation(mCtx, Glide.get(mCtx).getBitmapPool(), oop.getBlurRadius(), oop.getBlurSampling()).transformToView((Bitmap) resource, view, oop.getEffectOutputView());
                                                return false;
                                            }
                                        });
                                    }
                                } else {
                                    new BlurTransformation(mCtx, Glide.get(mCtx).getBitmapPool(), oop.getBlurRadius(), oop.getBlurSampling()).transformToView((Bitmap) resource, view, oop.getEffectOutputView());
                                }
                            }
                        }
                        return false;
                    }
                };
                if (imageViewWeakReference.get() != null) {
                    builder.listener(requestListener).into(imageViewWeakReference.get());
                }
            } else {
                if (imageViewWeakReference.get() != null) {
                    builder.into(imageViewWeakReference.get());
                }
            }
        }
    }

    @Override
    public void cancel(ImageView imageView) {
        Glide.with(mCtx).clear(imageView);
    }

    @Override
    public void pauseRequest() {
        Glide.with(mCtx).pauseRequests();
    }

    @Override
    public void resumeRequest() {
        Glide.with(mCtx).resumeRequests();
    }

    @Override
    public void clearMemoryCache() {
        Glide.get(mCtx).clearMemory();
    }

    @Override
    public void clearDiskCache() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Glide.get(mCtx).clearDiskCache();
            }
        }).start();
    }

    @Override
    public void onTrimMemory(int level) {
        Glide.get(mCtx).trimMemory(level);
    }

    @Override
    public void onLowMemory() {
        Glide.get(mCtx).onLowMemory();
    }

    @Override
    public void init(Context context, InitOption option) {
        mInitOption = option;
        mCtx = context;
        Glide.get(context.getApplicationContext()).setMemoryCategory(MemoryCategory.NORMAL);
        mReqListener = new RequestListener() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(Object resource, Object model, Target target, DataSource dataSource, boolean isFirstResource) {
                return false;
            }
        };
        try {
            if (mInitOption.getOptionFactory() == null) {
                mInitOption.optionFactory(new GlideOptionFactory(context));
            }
            if (mInitOption.getOptionFactory().get(DisplayOptionFactory.getDefaultOptionName()) == null) {
                mInitOption.getOptionFactory().add(DisplayOptionFactory.getDefaultOptionName(), new DisplayOption());
            }
        } catch (NullPointerException e) {
        }
    }

    @Override
    public void release() {
        if (mReqListener != null) {
            if (Looper.getMainLooper() == Looper.myLooper()) {
                clearMemoryCache();
                Glide.with(mCtx).onDestroy();
                mReqListener = null;
            } else {
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        clearMemoryCache();
                        Glide.with(mCtx).onDestroy();
                        mReqListener = null;
                    }
                });
            }
        }
    }
}
