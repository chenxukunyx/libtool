package com.shentu.lib_mvp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.AutoDisposeConverter;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Lifecycle;

/**
 * Created with Android Studio
 *
 * @author: chenxukun
 * @date: 2019/2/26
 * @time: 1:29 PM
 * @fuction:
 */
public abstract class BaseInjectMvpFragment extends BaseFragment implements IBaseView {

    private List<BasePresenter> mPresenters;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
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
                } catch (java.lang.InstantiationException e) {
                    e.printStackTrace();
                }
            }
        }
        return view;
    }

    @Override
    public void onDestroyView() {
        for (BasePresenter presenter : mPresenters) {
            presenter.detachView();
        }
        super.onDestroyView();
    }

    @Override
    public <T> AutoDisposeConverter<T> bindAutoDispose() {
        return AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY));
    }
}
