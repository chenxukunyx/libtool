package com.shentu.lib_view.banner;


import com.shentu.lib_view.banner.transformer.AccordionTransformer;
import com.shentu.lib_view.banner.transformer.BackgroundToForegroundTransformer;
import com.shentu.lib_view.banner.transformer.CubeInTransformer;
import com.shentu.lib_view.banner.transformer.CubeOutTransformer;
import com.shentu.lib_view.banner.transformer.DefaultTransformer;
import com.shentu.lib_view.banner.transformer.DepthPageTransformer;
import com.shentu.lib_view.banner.transformer.FlipHorizontalTransformer;
import com.shentu.lib_view.banner.transformer.FlipVerticalTransformer;
import com.shentu.lib_view.banner.transformer.ForegroundToBackgroundTransformer;
import com.shentu.lib_view.banner.transformer.RotateDownTransformer;
import com.shentu.lib_view.banner.transformer.RotateUpTransformer;
import com.shentu.lib_view.banner.transformer.ScaleInOutTransformer;
import com.shentu.lib_view.banner.transformer.StackTransformer;
import com.shentu.lib_view.banner.transformer.TabletTransformer;
import com.shentu.lib_view.banner.transformer.ZoomInTransformer;
import com.shentu.lib_view.banner.transformer.ZoomOutSlideTransformer;
import com.shentu.lib_view.banner.transformer.ZoomOutTransformer;

import androidx.viewpager.widget.ViewPager;

public class Transformer {
    public static Class<? extends ViewPager.PageTransformer> Default = DefaultTransformer.class;
    public static Class<? extends ViewPager.PageTransformer> Accordion = AccordionTransformer.class;
    public static Class<? extends ViewPager.PageTransformer> BackgroundToForeground = BackgroundToForegroundTransformer.class;
    public static Class<? extends ViewPager.PageTransformer> ForegroundToBackground = ForegroundToBackgroundTransformer.class;
    public static Class<? extends ViewPager.PageTransformer> CubeIn = CubeInTransformer.class;
    public static Class<? extends ViewPager.PageTransformer> CubeOut = CubeOutTransformer.class;
    public static Class<? extends ViewPager.PageTransformer> DepthPage = DepthPageTransformer.class;
    public static Class<? extends ViewPager.PageTransformer> FlipHorizontal = FlipHorizontalTransformer.class;
    public static Class<? extends ViewPager.PageTransformer> FlipVertical = FlipVerticalTransformer.class;
    public static Class<? extends ViewPager.PageTransformer> RotateDown = RotateDownTransformer.class;
    public static Class<? extends ViewPager.PageTransformer> RotateUp = RotateUpTransformer.class;
    public static Class<? extends ViewPager.PageTransformer> ScaleInOut = ScaleInOutTransformer.class;
    public static Class<? extends ViewPager.PageTransformer> Stack = StackTransformer.class;
    public static Class<? extends ViewPager.PageTransformer> Tablet = TabletTransformer.class;
    public static Class<? extends ViewPager.PageTransformer> ZoomIn = ZoomInTransformer.class;
    public static Class<? extends ViewPager.PageTransformer> ZoomOut = ZoomOutTransformer.class;
    public static Class<? extends ViewPager.PageTransformer> ZoomOutSlide = ZoomOutSlideTransformer.class;
}
