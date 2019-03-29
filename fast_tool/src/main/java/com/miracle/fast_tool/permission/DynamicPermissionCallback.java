package com.miracle.fast_tool.permission;


public interface DynamicPermissionCallback {

    void onGetPermissionSuccess();
    void onGetPermissionFailure(String state);
}
