package com.shentu.lib_image.options;

import android.content.Context;
import android.graphics.Bitmap;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.CenterInside;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.FitCenter;
import com.bumptech.glide.request.RequestOptions;
import com.shentu.lib_image.transform.BlurTransformation;
import com.shentu.lib_image.transform.GrayscaleTransformation;
import com.shentu.lib_image.transform.MaskTransformation;
import com.shentu.lib_image.transform.RoundedCornersTransformation;

import java.util.ArrayList;
import java.util.List;


public class GlideOptionFactory extends DisplayOptionFactory<RequestOptions> {
    /**
     * application context
     */
    Context mContext;
    /**
     * cornerType转换队列
     */
    List<Integer> mRadiusConverter = new ArrayList<>();

    public GlideOptionFactory(Context context) {
        mContext = context.getApplicationContext();
    }

    /**
     * 转换为glide cornerType
     *
     * param op
     * return
     */
    RoundedCornersTransformation.CornerType getGlideCornerType(DisplayOption op) {

        /**
         * 第一次使用初始化转换队列
         */
        if (mRadiusConverter.size() == 0) {
            mRadiusConverter.add(RoundedCornersTransformation.CornerType.ALL.ordinal(), DisplayOption.RadiusAll);
            mRadiusConverter.add(RoundedCornersTransformation.CornerType.TOP_LEFT.ordinal(), DisplayOption.RadiusTopLeft);
            mRadiusConverter.add(RoundedCornersTransformation.CornerType.TOP_RIGHT.ordinal(), DisplayOption.RadiusTopRight);
            mRadiusConverter.add(RoundedCornersTransformation.CornerType.BOTTOM_LEFT.ordinal(), DisplayOption.RadiusBottomLeft);
            mRadiusConverter.add(RoundedCornersTransformation.CornerType.BOTTOM_RIGHT.ordinal(), DisplayOption.RadiusBottomRight);
            mRadiusConverter.add(RoundedCornersTransformation.CornerType.TOP.ordinal(), DisplayOption.RadiusTop);
            mRadiusConverter.add(RoundedCornersTransformation.CornerType.BOTTOM.ordinal(), DisplayOption.RadiusBottom);
            mRadiusConverter.add(RoundedCornersTransformation.CornerType.LEFT.ordinal(), DisplayOption.RadiusLeft);
            mRadiusConverter.add(RoundedCornersTransformation.CornerType.RIGHT.ordinal(), DisplayOption.RadiusRight);
            mRadiusConverter.add(RoundedCornersTransformation.CornerType.OTHER_TOP_LEFT.ordinal(), DisplayOption.RadiusOtherTopLeft);
            mRadiusConverter.add(RoundedCornersTransformation.CornerType.OTHER_TOP_RIGHT.ordinal(), DisplayOption.RadiusOtherTopRight);
            mRadiusConverter.add(RoundedCornersTransformation.CornerType.OTHER_BOTTOM_LEFT.ordinal(), DisplayOption.RadiusOtherBottomLeft);
            mRadiusConverter.add(RoundedCornersTransformation.CornerType.OTHER_BOTTOM_RIGHT.ordinal(), DisplayOption.RadiusOtherBottomRight);
            mRadiusConverter.add(RoundedCornersTransformation.CornerType.DIAGONAL_FROM_TOP_LEFT.ordinal(), DisplayOption.RadiusDiagonalTopLeft);
            mRadiusConverter.add(RoundedCornersTransformation.CornerType.DIAGONAL_FROM_TOP_RIGHT.ordinal(), DisplayOption.RadiusDiagoalTopRight);

        }

        return RoundedCornersTransformation.CornerType.values()[mRadiusConverter.indexOf(op.getRadiusPosition())];
    }

    @Override
    public RequestOptions toTargetOptions(DisplayOption op) {
        RequestOptions options = new RequestOptions();

        if (op.getBitmapConfig() == Bitmap.Config.ARGB_8888) {
            options.format(DecodeFormat.PREFER_ARGB_8888);
        } else {
            options.format(DecodeFormat.PREFER_RGB_565);
        }

        if (op.getOverrideHeight() != 0 && op.getOverrideWidth() != 0) {
            options.override(op.getOverrideWidth(), op.getOverrideHeight());
        }

        if (op.getCacheOnDisc()) {
            if (op.isGif()) {
                options.diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);
            } else {
                options.diskCacheStrategy(DiskCacheStrategy.DATA);
                options.dontAnimate();
            }
        } else {
            options.diskCacheStrategy(DiskCacheStrategy.NONE);
        }

        options.skipMemoryCache(!op.getCacheInMemory());

        if (op.getLoadingImageResId() != -1) {
            options.placeholder(op.getLoadingImageResId());
        } else if (op.getLoadingImage() != null) {
            options.placeholder(op.getLoadingImage());
        }
        if (op.getErrorImageResId() != -1) {
            options.error(op.getErrorImageResId());
        } else if (op.getErrorImage() != null) {
            options.error(op.getErrorImage());
        }

        List<Transformation> transformationList = new ArrayList<Transformation>();
        if (mContext != null) {
            if ((op.getEffectOption() & DisplayOption.EffectOption.Blur) != 0) {
                options.format(DecodeFormat.PREFER_ARGB_8888);
                if (op.getEffectOutputView() == null) {
                    transformationList.add(new BlurTransformation(mContext, Glide.get(mContext).getBitmapPool(), op.getBlurRadius(), op.getBlurSampling()));
                }
            }
            if ((op.getEffectOption() & DisplayOption.EffectOption.Mask) != 0 && op.getMaskImageResId() != -1) {
                transformationList.add(new MaskTransformation(mContext, op.getMaskImageResId(), op.getBackgroundColor(), op.getBitmapConfig()));
            }
            if ((op.getEffectOption() & DisplayOption.EffectOption.GrayScale) != 0) {
                transformationList.add(new GrayscaleTransformation(mContext));
            }
        }

        if (op.getFitOption() == DisplayOption.FitOption.None) {
            //dont' addd transformation
        } else if (op.getFitOption() == DisplayOption.FitOption.FitCenter) {
            transformationList.add(new FitCenter());
        } else if (op.getFitOption() == DisplayOption.FitOption.CircleCrop) {
            transformationList.add(new CircleCrop());
        } else if (op.getFitOption() == DisplayOption.FitOption.CenterInside) {
            transformationList.add(new CenterInside());
        } else {
            transformationList.add(new CenterCrop());
        }

        try {
            if (op.getRadius() != 0 && mContext != null) {
                transformationList.add(new RoundedCornersTransformation(mContext, op.getRadius(), 0, op.getBackgroundColor(), getGlideCornerType(op), op.getBitmapConfig()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (transformationList.size() > 0) {
            MultiTransformation multiTransformation = new MultiTransformation(transformationList);
            options.transform(multiTransformation);
        }

        return options;
    }
}
