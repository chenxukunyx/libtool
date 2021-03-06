package com.shentu.lib_image.transform;

/**
 * Copyright (C) 2017 Wasabeef
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapResource;
import com.shentu.lib_image.transform.internal.FastBlur;

import java.security.MessageDigest;

public class BlurTransformation implements Transformation<Bitmap> {

    private static int MAX_RADIUS = 25;
    private static int DEFAULT_DOWN_SAMPLING = 1;

    private BitmapPool mBitmapPool;

    private int mRadius;
    private int mSampling;

    public BlurTransformation(Context context) {
        this(context, Glide.get(context).getBitmapPool(), MAX_RADIUS, DEFAULT_DOWN_SAMPLING);
    }

    public BlurTransformation(Context context, BitmapPool pool) {
        this(context, pool, MAX_RADIUS, DEFAULT_DOWN_SAMPLING);
    }

    public BlurTransformation(Context context, BitmapPool pool, int radius) {
        this(context, pool, radius, DEFAULT_DOWN_SAMPLING);
    }

    public BlurTransformation(Context context, int radius) {
        this(context, Glide.get(context).getBitmapPool(), radius, DEFAULT_DOWN_SAMPLING);
    }

    public BlurTransformation(Context context, int radius, int sampling) {
        this(context, Glide.get(context).getBitmapPool(), radius, sampling);
    }

    public BlurTransformation(Context context, BitmapPool pool, int radius, int sampling) {
        mBitmapPool = pool;
        mRadius = radius;
        mSampling = sampling;
    }

    public void transformToView(Bitmap source, View sourceView, final View outputView) {
        int scaledWidth;
        int scaledHeight;
        float translateX;
        float translateY;

        if (sourceView != null) {
            float dx = (float)source.getWidth()/sourceView.getMeasuredWidth()/mSampling;
            float dy = (float)source.getHeight()/sourceView.getMeasuredHeight()/mSampling;
            scaledHeight = (int)(outputView.getMeasuredHeight()*dy);
            scaledWidth = (int)(outputView.getMeasuredWidth()*dx);
            translateX = -outputView.getLeft()*dx;
            translateY = -outputView.getTop()*dy;
        } else {
            scaledWidth = outputView.getWidth() / mSampling;
            scaledHeight = outputView.getHeight() / mSampling;
            translateX = -outputView.getLeft() / mSampling;
            translateY = -outputView.getTop() / mSampling;
        }

        if (scaledHeight == 0 || scaledWidth == 0) {
            Log.d("blurtest", "size==0");
            return;
        }
        Bitmap bitmap = mBitmapPool.get(scaledWidth, scaledHeight, Bitmap.Config.ARGB_8888);
        if (bitmap == null) {
            bitmap = Bitmap.createBitmap(scaledWidth, scaledHeight, Bitmap.Config.ARGB_8888);
        }
        Canvas canvas = new Canvas(bitmap);
        canvas.translate(translateX, translateY);
        canvas.scale(1 / (float) mSampling, 1 / (float) mSampling);
        Paint paint = new Paint();
        paint.setFlags(Paint.FILTER_BITMAP_FLAG);
        canvas.drawBitmap(source, 0, 0, paint);

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
//            try {
//                bitmap = RSBlur.blur(outputView.getContext(), bitmap, mRadius);
//            } catch (RSRuntimeException e) {
//                bitmap = FastBlur.blur(bitmap, mRadius, true);
//            }
//        } else {
        bitmap = FastBlur.blur(bitmap, mRadius, true);
//        }
        outputView.setBackground(new BitmapDrawable(outputView.getResources(), bitmap));
        outputView.invalidate();
        Log.d("blur", "outputView");
    }

    @Override
    public Resource<Bitmap> transform(Context context, Resource<Bitmap> resource, int outWidth, int outHeight) {
        Bitmap source = resource.get();

        int width = source.getWidth();
        int height = source.getHeight();
        int scaledWidth = width / mSampling;
        int scaledHeight = height / mSampling;

        Bitmap.Config config =
                source.getConfig() != null ? source.getConfig() : Bitmap.Config.ARGB_8888;
        Bitmap bitmap = mBitmapPool.get(scaledWidth, scaledHeight, config);
        if (bitmap == null) {
            bitmap = Bitmap.createBitmap(scaledWidth, scaledHeight, config);
        }

        Canvas canvas = new Canvas(bitmap);
        canvas.scale(1 / (float) mSampling, 1 / (float) mSampling);
        Paint paint = new Paint();
        paint.setFlags(Paint.FILTER_BITMAP_FLAG);
        canvas.drawBitmap(source, 0, 0, paint);

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
//            try {
//                bitmap = RSBlur.blur(context, bitmap, mRadius);
//            } catch (RSRuntimeException e) {
//                bitmap = FastBlur.blur(bitmap, mRadius, true);
//            }
//        } else {
        bitmap = FastBlur.blur(bitmap, mRadius, true);
//        }

        return BitmapResource.obtain(bitmap, mBitmapPool);
    }

    private String getId() {
        return ("BlurTransformation(radius=" + mRadius + ", sampling=" + mSampling + ")");
    }

    @Override
    public boolean equals(Object obj) {
        return (hashCode() == obj.hashCode());
    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }

    @Override
    public void updateDiskCacheKey(MessageDigest messageDigest) {
        messageDigest.update(getId().getBytes());
    }
}
