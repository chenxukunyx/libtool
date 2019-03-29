package com.miracle.fast_tool;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.miracle.fast_tool.permission.DynamicPermissionCallback;
import com.miracle.fast_tool.permission.Permission;
import com.miracle.fast_tool.permission.RealRxPermission;
import com.miracle.fast_tool.permission.RxPermission;
import com.miracle.fast_tool.utils.LogUtil;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

import java.util.HashSet;
import java.util.Set;


public class BasePermissionFragment extends Fragment {

    private static final String TAG = "BasePermissionFragment";

    private Set<String> mPressionList = new HashSet<>();
    protected RxPermission mRxPermissions;


    private void addPermission(String... permissions) {
        if (permissions != null && permissions.length > 0) {
            for (String p : permissions) {
                mPressionList.add(p);
            }
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRxPermissions = RealRxPermission.getInstance(BaseApplication.getContext());
        requestPermission();
    }

    protected String[] addPermission() {
        return null;
    }

    protected void requestPermission() {
        if (mPressionList.size() > 0) {
            mRxPermissions.requestEach(mPressionList.toArray(new String[mPressionList.size()]))
                    .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this)))
                    .subscribe(new Consumer<Permission>() {
                        @Override
                        public void accept(Permission permission) throws Exception {
                            if (permission.state() == Permission.State.GRANTED) {
                                LogUtil.i(TAG, "get permission success");
                            } else if (permission.state() == Permission.State.DENIED) {
                                LogUtil.i(TAG, "get permission failed, next time requset");
                            } else if (permission.state() == Permission.State.DENIED_NOT_SHOWN) {
                                LogUtil.i(TAG, "get permission failed, no request again");
                            }
                        }
                    });
        }
    }


    protected void outputLog(Object msg) {
        Log.i(TAG, msg.toString());
    }

    protected void dynamicRequestPermission(String[] permissions, DynamicPermissionCallback callback) {
        mRxPermissions.requestEach(permissions)
                .compose(new ObservableTransformer<Permission, Permission>() {
                    @Override
                    public ObservableSource<Permission> apply(Observable<Permission> upstream) {
                        return upstream.flatMap(new Function<Permission, ObservableSource<Permission>>() {
                            @Override
                            public ObservableSource<Permission> apply(Permission permission) throws Exception {
                                if (permission.state() == Permission.State.DENIED) {
                                    Observable.error(new Exception("未获取权限，将在下次事件触发时重新请求"));
                                } else if (permission.state() == Permission.State.DENIED_NOT_SHOWN) {
                                    Observable.error(new Exception("未获取权限，请到应用授权中心授权后使用"));
                                } else {
                                    Observable.error(new Exception("REVOKED_BY_POLICY"));
                                }
                                return Observable.just(permission);
                            }
                        });
                    }
                })
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this)))
                .subscribe(new Observer<Permission>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Permission permission) {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onGetPermissionFailure(e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        callback.onGetPermissionSuccess();
                    }
                });
    }
}
