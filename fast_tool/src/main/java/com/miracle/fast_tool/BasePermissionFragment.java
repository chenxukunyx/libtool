package com.miracle.fast_tool;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.miracle.fast_tool.permission.Permission;
import com.miracle.fast_tool.permission.RxPermissionCallback;
import com.miracle.fast_tool.permission.RxPermissionState;
import com.miracle.fast_tool.permission.RxPermissions;
import com.miracle.fast_tool.utils.LogUtil;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.functions.Consumer;

import java.util.HashSet;
import java.util.Set;


public class BasePermissionFragment extends Fragment {

    private static final String TAG = "BasePermissionFragment";

    private Set<String> mPressionList = new HashSet<>();
    protected RxPermissions mRxPermissions;

    private void addPermission(String... permissions) {
        if (permissions != null && permissions.length > 0) {
            for (String p : permissions) {
                mPressionList.add(p);
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRxPermissions = new RxPermissions(this);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        requestPermission();
    }

    protected String[] addPermission() {
        return null;
    }

    protected void requestPermission() {
        if (mPressionList.size() > 0) {
            mRxPermissions.requestEach(mPressionList.toArray(new String[mPressionList.size()]))
                    .as(AutoDispose.<Permission>autoDisposable(AndroidLifecycleScopeProvider.from(this)))
                    .subscribe(new Consumer<Permission>() {
                        @Override
                        public void accept(Permission permission) throws Exception {
                            if (permission.granted) {
                                LogUtil.i(TAG, "get permission success");
                            } else if (permission.shouldShowRequestPermissionRationale) {
                                LogUtil.i(TAG, "get permission failed, next time requset");
//                            ToastMng.INSTANCE.showToast("拒绝访问，等待下次询问哦~");
                            } else {
                                LogUtil.i(TAG, "get permission failed, no request again");
//                            ToastMng.INSTANCE.showToast("拒绝权限，请前往应用权限管理中打开权限~");
                            }

                        }
                    });
        }
    }

    protected void requestPermission(final RxPermissionCallback callback, String... permissions) {
        mRxPermissions.requestEach(permissions)
                .as(AutoDispose.<Permission>autoDisposable(AndroidLifecycleScopeProvider.from(this)))
                .subscribe(new Consumer<Permission>() {
                    @Override
                    public void accept(Permission permission) throws Exception {
                        if (permission.granted) {
                            callback.permissionState(RxPermissionState.GRANTED, permission);
                            LogUtil.i(TAG, "get permission success");
                        } else if (permission.shouldShowRequestPermissionRationale) {
                            LogUtil.i(TAG, "get permission failed, next time requset");
//                            ToastMng.INSTANCE.showToast("拒绝访问，等待下次询问哦~");
                            callback.permissionState(RxPermissionState.SHOULDSHOWREQUESTPERMISSIONRATIONALE, permission);
                        } else {
                            LogUtil.i(TAG, "get permission failed, no request again");
//                            ToastMng.INSTANCE.showToast("拒绝权限，请前往应用权限管理中打开权限~");
                            callback.permissionState(RxPermissionState.REFUSE, permission);
                        }

                    }
                });
    }

    protected void outputLog(Object msg) {
        Log.i(TAG, msg.toString());
    }
}
