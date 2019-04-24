package com.shentu.lib_mvp;

/**
 * Created with Android Studio
 *
 * @author: chenxukun
 * @date: 2019/2/26
 * @time: 1:16 PM
 * @fuction:
 */
public class BasePresenter<V extends IBaseView> {

    protected V mView;

    /**
     * 绑定View
     * @param view
     */
    public void attachView(V view) {
        mView = view;
    }

    /**
     * 解绑view 防止内存泄漏
     */
    public void detachView() {
        mView = null;
    }

    /**
     * View 是否绑定
     * @return
     */
    protected boolean isViewAttached() {
        return mView != null;
    }
}
