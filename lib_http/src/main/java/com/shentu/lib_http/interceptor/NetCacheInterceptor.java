package com.shentu.lib_http.interceptor;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created with Android Studio
 *
 * @author: chenxukun
 * @date: 2019/2/25
 * @time: 2:19 PM
 * @fuction:
 */
public class NetCacheInterceptor implements Interceptor {

    //是否需要加载缓存，不需要加载缓存时，直接将maxAge设置为0，在有网的情况下会直接请求网络
    private boolean needCache;

    public NetCacheInterceptor() {
        this(true);
    }

    public NetCacheInterceptor(boolean needCache) {
        this.needCache = needCache;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);
        int maxAge;
        if (needCache) {
            maxAge = 30;
        } else {
            maxAge = 0;
        }
        return response.newBuilder()
                .header("Cache-Control", "public, max-age=" + maxAge)
                .removeHeader("Pragma")
                .build();
    }
}
