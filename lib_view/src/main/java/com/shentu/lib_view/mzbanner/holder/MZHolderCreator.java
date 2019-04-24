package com.shentu.lib_view.mzbanner.holder;

/**
 * Created with Android Studio
 *
 * @author: chenxukun
 * @date: 2019/2/28
 * @time: 4:47 PM
 * @fuction:
 */
public interface MZHolderCreator<VH extends MZViewHolder> {

    /**
     * 创建ViewHolder
     * @return
     */
    public VH createViewHolder();
}
