package com.miracle.fast_tool.permission;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.annotations.CheckReturnValue;
import io.reactivex.annotations.NonNull;

/**
 * Created with Android Studio
 *
 * @author: chenxukun
 * @date: 2019/3/8
 * @time: 2:43 PM
 * @fuction:
 */
public interface RxPermission {

    /**
     * Requests a single permission.
     */
    @NonNull
    @CheckReturnValue
    Single<Permission> request(@NonNull String permission);

    /**
     * Requests multiple permissions.
     */
    @NonNull
    @CheckReturnValue
    Observable<Permission> requestEach(@NonNull String... permissions);

    /**
     * Returns true when the given permission is granted.
     */
    @CheckReturnValue
    boolean isGranted(@NonNull String permission);

    /**
     * Returns true when the given permission is revoked by a policy.
     */
    @CheckReturnValue
    boolean isRevokedByPolicy(@NonNull String permission);
}