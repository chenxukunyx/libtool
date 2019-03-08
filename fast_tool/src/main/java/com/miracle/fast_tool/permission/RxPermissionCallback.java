package com.miracle.fast_tool.permission;

/**
 * Created with Android Studio
 *
 * @author: chenxukun
 * @date: 2019/3/8
 * @time: 12:54 PM
 * @fuction:
 */
public interface RxPermissionCallback {

    void permissionState(RxPermissionState state, Permission permission);
}
