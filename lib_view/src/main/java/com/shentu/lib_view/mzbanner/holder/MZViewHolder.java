package com.shentu.lib_view.mzbanner.holder;

import android.content.Context;
import android.view.View;

/**
 * Created with Android Studio
 *
 * @author: chenxukun
 * @date: 2019/2/28
 * @time: 4:48 PM
 * @fuction:
 */
public interface MZViewHolder<T> {
    /**
     *  创建View
     * @param context
     * @return
     */
    View createView(Context context);

    /**
     * 绑定数据
     * @param context
     * @param position
     * @param data
     */
    void onBind(Context context, int position, T data);
}
