package com.shentu.lib_mvp;

import android.app.Dialog;
import android.content.Context;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;

/**
 * Created with Android Studio
 *
 * @author: chenxukun
 * @date: 2019/3/12
 * @time: 10:16 AM
 * @fuction:
 */
public class LoadingProgress extends Dialog {

    ProgressBar mLoading;


    public LoadingProgress(@NonNull Context context) {
        super(context, R.style.CustomDialog);
        setContentView(R.layout.dialog_loading_progress);

        mLoading = findViewById(R.id.progress);

        setCanceledOnTouchOutside(false);
    }

    public void showLoading() {
        super.show();
    }

    public void hideLoading() {
        super.cancel();
    }
}
