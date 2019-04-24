package com.shentu.lib_http;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.shentu.lib_http.api.API;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created with Android Studio
 *
 * @author: chenxukun
 * @date: 2019/2/25
 * @time: 3:24 PM
 * @fuction:
 * T ApiService
 */
public class HttpManager<T> {


    /**
     * Okhttp 的addInterceptor 和 addNetworkInterceptor 的区别？
     * <p>
     * addInterceptor（应用拦截器）：
     * <p>
     * 1，不需要担心中间过程的响应,如重定向和重试.
     * <p>
     * 2，总是只调用一次,即使HTTP响应是从缓存中获取.
     * <p>
     * 3，观察应用程序的初衷. 不关心OkHttp注入的头信息如: If-None-Match.
     * <p>
     * 4，允许短路而不调用 Chain.proceed(),即中止调用.
     * <p>
     * 5，允许重试,使 Chain.proceed()调用多次.
     * <p>
     * addNetworkInterceptor（网络拦截器）：
     * <p>
     * 1，能够操作中间过程的响应,如重定向和重试.
     * <p>
     * 2，当网络短路而返回缓存响应时不被调用.
     * <p>
     * 3，只观察在网络上传输的数据.
     * <p>
     * 4，携带请求来访问连接.
     */

    private OkHttpClient mOkHttpClient;
    private Retrofit mRetrofit;
    private Class<T> apiServiceClass;


    public HttpManager(String baseUrl, OkHttpClient.Builder builder, Class apiServiceClass) {
        if (apiServiceClass == null) throw new IllegalArgumentException("apiServiceClass = null");
        if (baseUrl == null || baseUrl.isEmpty())
            baseUrl = API.BASE_URL;

        this.apiServiceClass = apiServiceClass;
        mOkHttpClient = builder
                .build();

        mRetrofit = new Retrofit.Builder()
                .client(mOkHttpClient)
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public HttpManager(Class apiServiceClass) {
        this(API.BASE_URL, new OkHttpClient.Builder(), apiServiceClass);
    }

    public HttpManager(OkHttpClient.Builder builder, Class apiServiceClass) {
        this(API.BASE_URL, builder, apiServiceClass);
    }

    /**
     * 创建apiService
     * @return
     */
    public T create() {
        T apiService = mRetrofit.create(apiServiceClass);
        return apiService;
    }

    public OkHttpClient getOkHttpClient() {
        return mOkHttpClient;
    }

}
