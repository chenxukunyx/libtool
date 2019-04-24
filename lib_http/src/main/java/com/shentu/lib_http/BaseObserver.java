package com.shentu.lib_http;

import com.shentu.lib_http.exception.ApiException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created with Android Studio
 *
 * @author: chenxukun
 * @date: 2019/2/28
 * @time: 9:57 AM
 * @fuction:
 */
public abstract class BaseObserver<T> implements Observer<T> {

    public void showLoading() {

    }

    public void hideLoading() {

    }

    public abstract void onSuccess(T t);

    public abstract void onFailure(ErrorMessage message);



    @Override
    public void onSubscribe(Disposable d) {
        showLoading();
    }

    @Override
    public void onNext(T t) {
        onSuccess(t);
    }

    @Override
    public void onError(Throwable e) {
        hideLoading();
        ApiException exception = ApiException.handleException(e);
        if (exception == null) {
            onFailure(new ErrorMessage(e.getMessage(), e.getCause().getMessage()));
        } else {
//            String msg = exception.getCode() + ", " + exception.getMessage();
            onFailure(new ErrorMessage(exception.getCode() + "", exception.getMessage()));
        }
    }

    @Override
    public void onComplete() {
        hideLoading();
    }
}
