package com.shentu.lib_view.mzbanner.transformer;

import android.view.View;

import androidx.viewpager.widget.ViewPager;

/**
 * Created with Android Studio
 *
 * @author: chenxukun
 * @date: 2019/2/28
 * @time: 4:49 PM
 * @fuction:
 */
public class ScaleYTransformer implements ViewPager.PageTransformer {
    private static final float MIN_SCALE = 0.9F;
    @Override
    public void transformPage(View page, float position) {

        if(position < -1){
            page.setScaleY(MIN_SCALE);
        }else if(position<= 1){
            //
            float scale = Math.max(MIN_SCALE,1 - Math.abs(position));
            page.setScaleY(scale);
            /*page.setScaleX(scale);
            if(position<0){
                page.setTranslationX(width * (1 - scale) /2);
            }else{
                page.setTranslationX(-width * (1 - scale) /2);
            }*/

        }else{
            page.setScaleY(MIN_SCALE);
        }
    }

}
