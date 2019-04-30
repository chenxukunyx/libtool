package com.shentu.lib_http.transformer;

import com.shentu.lib_http.bean.BaseResponse;
import com.shentu.lib_http.exception.ApiException;
import com.shentu.lib_http.exception.TokenExpireException;
import com.shentu.lib_http.exception.TokenInvalidException;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class RxScheduler {

    static final ObservableTransformer schedulersTransformer = new ObservableTransformer() {
        @Override
        public ObservableSource apply(Observable upstream) {
            return upstream
                    .subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        }
    };

    /**
     * 有BaseResponse嵌套的情况，使用此方法
     */
    public static <T> ObservableTransformer<BaseResponse<T>, BaseResponse<T>> baseSchedulers() {
        return (ObservableTransformer<BaseResponse<T>, BaseResponse<T>>) schedulersTransformer;
    }


    public static <T> ObservableTransformer<BaseResponse<T>, T> schdulerAndHandleBaseResponse() {
        return upstream ->
                upstream.subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .onErrorResumeNext(throwable -> {
                            return Observable.error(ApiException.handleException(throwable));
                        })
                        .flatMap(new BaseResponseFunc<>());


    }

    public static class BaseResponseFunc<T> implements Function<BaseResponse<T>, ObservableSource<T>> {
        @Override
        public ObservableSource<T> apply(BaseResponse<T> tBaseResponse) throws Exception {
            if (tBaseResponse.isSuccessful()) {
                if (tBaseResponse.getData() == null) {
                    return Observable.empty();
                }
                return Observable.just(tBaseResponse.getData());
            } else {
                if (tBaseResponse.getState().equals("3")) {//token过期
                    return Observable.error(new TokenExpireException(tBaseResponse.getState(), new Throwable(tBaseResponse.getMsg())));
                } else if (tBaseResponse.getState().equals("2")) {//token失效
                    return Observable.error(new TokenInvalidException(tBaseResponse.getState(), new Throwable(tBaseResponse.getMsg())));
                }
                return Observable.error(new Exception(tBaseResponse.getState(), new Throwable(tBaseResponse.getMsg())));
            }
        }
    }


    /**
     * 没有BaseResponse嵌套的情况，使用此方法
     */
    public static <T> ObservableTransformer<T, T> schedulers() {
        return (ObservableTransformer<T, T>) schedulersTransformer;
    }


    /**
     * 没有BaseResponse嵌套的情况，使用此方法
     */
    public static <T extends BaseResponse> ObservableTransformer<T, T> schdulerAndHandleResponse() {
        return upstream ->
                upstream.subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .onErrorResumeNext(throwable -> {
                            return Observable.error(ApiException.handleException(throwable));
                        })
                        .flatMap(new ResponseFunc<>());


    }

    private static class ResponseFunc<T extends BaseResponse> implements Function<T, ObservableSource<T>> {
        @Override
        public ObservableSource<T> apply(T tBaseResponse) throws Exception {
            if (tBaseResponse.isSuccessful()) {
                return Observable.just(tBaseResponse);
            } else {
                return Observable.error(new Exception(tBaseResponse.getState(), new Throwable(tBaseResponse.getMsg())));
            }
        }
    }
}
