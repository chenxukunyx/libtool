package com.shentu.lib_mvp;

import com.uber.autodispose.AutoDisposeConverter;

/**
 * Created with Android Studio
 *
 * @author: chenxukun
 * @date: 2019/2/26
 * @time: 11:06 AM
 * @fuction:
 */
public interface IBaseView {

    /**
     * 加载中
     */
    void showLoading();

    /**
     * 加载完成
     */
    void hideLoading();

    /**
     * 失败回调
     * @param msg
     */
//    void onError(String msg);

    /**
     * 绑定Android生命周期，防止rxjava内存泄漏
     * @param <T>
     * @return
     */
    <T> AutoDisposeConverter<T> bindAutoDispose();
}
