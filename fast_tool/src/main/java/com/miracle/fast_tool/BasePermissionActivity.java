package com.miracle.fast_tool;

import android.os.Bundle;
import android.util.Log;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.miracle.fast_tool.permission.Permission;
import com.miracle.fast_tool.permission.RxPermissions;
import com.miracle.fast_tool.utils.LogUtil;
import com.miracle.fast_tool.utils.ToastMng;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.functions.Consumer;

import java.util.HashSet;
import java.util.Set;


public abstract class BasePermissionActivity extends AppCompatActivity {
    private static final String TAG = "BasePermissionActivity";

    private Set<String> mPressionList = new HashSet<>();
    protected RxPermissions mRxPermissions;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRxPermissions = new RxPermissions(this);
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
        if (mPressionList.size() >0) {
            mRxPermissions.requestEach(mPressionList.toArray(new String[mPressionList.size()]))
                    .as(AutoDispose.<Permission>autoDisposable(AndroidLifecycleScopeProvider.from(this)))
                    .subscribe(new Consumer<Permission>() {
                        @Override
                        public void accept(Permission permission) throws Exception {
                            if (permission.granted) {
                                LogUtil.i(TAG, "get permission success");
                            } else if (permission.shouldShowRequestPermissionRationale) {
                                LogUtil.i(TAG, "get permission failed, next time requset");
                                ToastMng.INSTANCE.showToast("拒绝访问，等待下次询问哦~");
                            } else {
                                LogUtil.i(TAG, "get permission failed, no request again");
                                ToastMng.INSTANCE.showToast("拒绝权限，请前往应用权限管理中打开权限~");
                            }

                        }
                    });
        }
    }

    protected void outputLog(Object msg) {
        Log.i(TAG, msg.toString());
    }
}
