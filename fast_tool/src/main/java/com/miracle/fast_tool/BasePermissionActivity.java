package com.miracle.fast_tool;

import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
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


public abstract class BasePermissionActivity extends AppCompatActivity {
    private static final String TAG = "BasePermissionActivity";

    private Set<String> mPressionList = new HashSet<>();
    protected RxPermission mRxPermissions;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRxPermissions = RealRxPermission.getInstance(BaseApplication.getContext());
        addPermission(addPermission());
        requestPermission();
    }

    private void addPermission(String... permissions) {
        if (permissions != null && permissions.length > 0) {
            for (String p : permissions) {
                mPressionList.add(p);
            }
        }
    }


    protected  String[] addPermission(){
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

    /**
     * 要求所有要请求的权限全部成功
     * @param permissions
     * @param callback
     */
    protected void dynamicRequestPermission(String[] permissions, DynamicPermissionCallback callback) {
        mRxPermissions.requestEach(permissions)
                .compose(new ObservableTransformer<Permission, Permission>() {
                    @Override
                    public ObservableSource<Permission> apply(Observable<Permission> upstream) {
                        return upstream.flatMap(new Function<Permission, ObservableSource<Permission>>() {
                            @Override
                            public ObservableSource<Permission> apply(Permission permission) throws Exception {
                                if (permission.state() == Permission.State.DENIED || permission.state() == Permission.State.DENIED_NOT_SHOWN) {
                                    if (permission.state() == Permission.State.DENIED) {
                                        return Observable.error(new Exception(Permission.State.DENIED.name()));
                                    } else {
                                        return Observable.error(new Exception(Permission.State.DENIED_NOT_SHOWN.name()));
                                    }
                                }
                                return Observable.just(permission);
                            }
                        });
                    }
                })
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
