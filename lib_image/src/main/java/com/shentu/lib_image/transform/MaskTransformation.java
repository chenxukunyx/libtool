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
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.Drawable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapResource;
import com.shentu.lib_image.transform.internal.Utils;

import java.security.MessageDigest;

public class MaskTransformation implements Transformation<Bitmap> {

    private static Paint sMaskingPaint = new Paint();

    static {
        sMaskingPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
    }

    private Context mContext;
    private BitmapPool mBitmapPool;
    private int mMaskId;
    private int mBgColor;
    private Bitmap.Config mBitmapConfig;

    /**
     * @param maskId If you change the mask file, please also rename the mask file, or Glide will get
     *               the cache with the old mask. Because getId() return the same values if using the
     *               same make file name. If you have a good idea please tell us, thanks.
     */
    public MaskTransformation(Context context, int maskId, int bgColor, Bitmap.Config bitmapConfig) {
        this(context, Glide.get(context).getBitmapPool(), maskId, bgColor, bitmapConfig);
    }

    public MaskTransformation(Context context, BitmapPool pool, int maskId, int bgColor, Bitmap.Config bitmapConfig) {
        mBitmapPool = pool;
        mContext = context.getApplicationContext();
        mMaskId = maskId;
        mBgColor = bgColor;
        mBitmapConfig = bitmapConfig;
    }

    @Override
    public Resource<Bitmap> transform(Context context, Resource<Bitmap> resource, int outWidth, int outHeight) {
        Bitmap source = resource.get();

        int width = source.getWidth();
        int height = source.getHeight();

        Bitmap.Config config = mBitmapConfig;
        if (config == null) {
            config = source.getConfig() != null ? source.getConfig() : Bitmap.Config.ARGB_8888;
        }
        Bitmap result = mBitmapPool.get(width, height, config);
        if (result == null) {
            result = Bitmap.createBitmap(width, height, config);
        }

        Drawable mask = Utils.getMaskDrawable(mContext, mMaskId);

        Canvas canvas = new Canvas(result);
        if (config == Bitmap.Config.RGB_565) {
            canvas.drawColor(mBgColor);
        }
        mask.setBounds(0, 0, width, height);
        mask.draw(canvas);
        canvas.drawBitmap(source, 0, 0, sMaskingPaint);

        return BitmapResource.obtain(result, mBitmapPool);
    }

    private String getId() {
        return ("MaskTransformation(maskId=" + mContext.getResources().getResourceEntryName(mMaskId)
                + ")");
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
