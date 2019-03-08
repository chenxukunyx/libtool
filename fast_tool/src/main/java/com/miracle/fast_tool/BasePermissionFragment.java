package com.miracle.fast_tool;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.miracle.fast_tool.permission.Permission;
import com.miracle.fast_tool.permission.RealRxPermission;
import com.miracle.fast_tool.permission.RxPermission;
import com.miracle.fast_tool.utils.LogUtil;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.functions.Consumer;

import java.util.HashSet;
import java.util.Set;


public class BasePermissionFragment extends Fragment {

    private static final String TAG = "BasePermissionFragment";

    private Set<String> mPressionList = new HashSet<>();
    protected RxPermission mRxPermissions = RealRxPermission.getInstance(getActivity().getApplicationContext());

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
}
