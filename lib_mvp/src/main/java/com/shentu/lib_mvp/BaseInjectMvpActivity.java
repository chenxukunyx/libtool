package com.shentu.lib_mvp;

import android.os.Bundle;

import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.AutoDisposeConverter;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.lifecycle.Lifecycle;

/**
 * Created with Android Studio
 *
 * @author: chenxukun
 * @date: 2019/3/1
 * @time: 9:41 AM
 * @fuction:
 */
public abstract class BaseInjectMvpActivity extends BaseActivity implements IBaseView{

    private List<BasePresenter> mPresenters;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenters = new ArrayList<>();

        Field[] fields = getClass().getDeclaredFields();
        for (Field field : fields) {
            InjectPresenter injectPresenter = field.getAnnotation(InjectPresenter.class);
            if (injectPresenter != null) {
                Class<? extends BasePresenter> clazz = null;
                try {
                    clazz = (Class<? extends BasePresenter>) field.getType();
                } catch (Exception e) {
                    throw new RuntimeException("not support inject presenter" + field.getType());
                }
                try {
                    BasePresenter presenter = clazz.newInstance();
                    presenter.attachView(this);
                    mPresenters.add(presenter);
                    field.setAccessible(true);
                    field.set(this, presenter);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                }
            }
        }
        initView();
    }

    @Override
    protected void onDestroy() {
        for (BasePresenter presenter : mPresenters) {
            presenter.detachView();
        }
        super.onDestroy();
    }

    @Override
    public <T> AutoDisposeConverter<T> bindAutoDispose() {
        return AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY));
    }
}
