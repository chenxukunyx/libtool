package com.shentu.lib_image;

//import android.content.Context;
//
//import com.bumptech.glide.GlideBuilder;
//import com.bumptech.glide.load.engine.cache.ExternalPreferredCacheDiskCacheFactory;
//import com.bumptech.glide.load.engine.cache.LruResourceCache;
//import com.bumptech.glide.load.engine.cache.MemorySizeCalculator;

//public class CustomAppGlideModule extends AppGlideModule {
//
//    @Override
//    public boolean isManifestParsingEnabled() {
//        return false;
//    }
//
//    @Override
//    public void applyOptions(Context context, GlideBuilder builder) {
//
//        /**
//         * 设置内存缓存2屏数据
//         */
//        MemorySizeCalculator calculator = new MemorySizeCalculator.Builder(context)
//                .setMemoryCacheScreens(2).setBitmapPoolScreens(2).build();
//        builder.setMemoryCache(new LruResourceCache(calculator.getMemoryCacheSize()));
//
//        /**
//         * 设置Sdcard缓存最大64MB
//         */
//        int diskCacheSizeBytes = 1024 * 1024 * InitOption.getmDefaultDiscCacheSize(); // 64 MB
//        builder.setDiskCache(new ExternalPreferredCacheDiskCacheFactory(context, diskCacheSizeBytes));
//    }
//}
