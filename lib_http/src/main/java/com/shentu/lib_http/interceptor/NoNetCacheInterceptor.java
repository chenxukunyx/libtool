package com.shentu.lib_http.interceptor;

import com.shentu.lib_tools.NetworkUtils;
import com.shentu.lib_tools.ToolsKernel;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created with Android Studio
 *
 * @author: chenxukun
 * @date: 2019/2/25
 * @time: 2:26 PM
 * @fuction:
 */
public class NoNetCacheInterceptor implements Interceptor {

    private boolean forceCache;
    private int maxStale;
    private TimeUnit timeunit;

    public NoNetCacheInterceptor() {
        this(false);
    }

    public NoNetCacheInterceptor(boolean forceCache) {
        this(forceCache, 60, TimeUnit.SECONDS);
    }

    public NoNetCacheInterceptor(boolean forceCache, int maxStale, TimeUnit timeunit) {
        this.forceCache = forceCache;
        this.maxStale = maxStale;
        this.timeunit = timeunit;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        if (!NetworkUtils.isNetworkConnected(ToolsKernel.getInstance.getContext())) {
            if (forceCache) {
                request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .build();
            } else {
                request = request.newBuilder()
                        .cacheControl(new CacheControl.Builder()
                                .maxStale(maxStale, timeunit)
                                .onlyIfCached()
                                .build())
//                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .build();

            }
        }
        return chain.proceed(request);
    }
}
