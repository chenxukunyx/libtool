package com.miracle.fast_tool.utils;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;
import com.miracle.fast_tool.BaseApplication;

public enum ToastMng {
    INSTANCE;

    private Toast mToast;
    private TextView mToastView;

    ToastMng() {
        mToast = Toast.makeText(BaseApplication.getContext(), "", Toast.LENGTH_SHORT);
//        mToast.setGravity(Gravity.CENTER, 0, SystemUtils.dip2px(UMApplication.getInstance(), 220));
//        View view = LayoutInflater.from(UMApplication.getInstance()).inflate(R.layout.common_toast, null);
//        mToastView = (TextView) view.findViewById(R.id.tv_toast);

        mToastView = new TextView(BaseApplication.getContext());
        mToastView.setPadding(40, 28, 40, 28);
        mToastView.setGravity(Gravity.CENTER);
        mToastView.setTextSize(TypedValue.COMPLEX_UNIT_PX, 32);
        mToastView.setTextColor(0xFFFFFFFF);

        RoundRectShape shape = new RoundRectShape(new float[]{12, 12, 12, 12, 12, 12, 12, 12}, null, null);
        ShapeDrawable drawable = new ShapeDrawable(shape);
        drawable.getPaint().setColor(0xFF484857);
        mToastView.setBackground(drawable);

        mToast.setGravity(Gravity.CENTER, 0, 0);
        mToast.setView(mToastView);
    }

    public void showToast(String msg) {
        if (mToastView != null) {
            mToastView.setText(msg);
        } else {
            mToast.setText(msg);
        }
        mToast.show();
    }

    public void showToast(int resId) {
        if (mToastView != null) {
            mToastView.setText(resId);
        } else {
            mToast.setText(resId);
        }
        mToast.show();
    }

    public ToastMng setPadding(int left, int top, int right, int bottom) {
        mToastView.setPadding(left, top, right, bottom);
        return this;
    }

    public ToastMng setTextSize(float size) {
        mToastView.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
        return this;
    }

    public ToastMng setTextColor(int color) {
        mToastView.setTextColor(color);
        return this;
    }

    public ToastMng setGravity(int gravity, int xOffset, int yOffse) {
        mToast.setGravity(gravity, xOffset, yOffse);
        return this;
    }

    public ToastMng setBackgroud(Drawable drawable) {
        mToastView.setBackground(drawable);
        return this;
    }

    public ToastMng setBackgroud(int resId) {
        mToastView.setBackgroundResource(resId);
        return this;
    }

}
