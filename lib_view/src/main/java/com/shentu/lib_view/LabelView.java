package com.shentu.lib_view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

/**
 * Created with Android Studio
 *
 * @author: chenxukun
 * @date: 2019/3/1
 * @time: 2:15 PM
 * @fuction:
 */
public class LabelView extends AppCompatTextView {

    private static final String Top = "置顶";
    private static final String Newest = "最新";
    private static final String Hotest = "热帖";

    private Context mContext;
    private Drawable mDrawable;
    private String label;

    public LabelView(Context context) {
        this(context, null);
    }

    public LabelView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LabelView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.LabelView);
        mDrawable = ta.getDrawable(R.styleable.LabelView_lv_background);
    }

    private Drawable getDrawable(Context context, String label) {
        if (label == null) {
            return null;
        }
        if (label.equals(Hotest)) {
            return context.getResources().getDrawable(R.drawable.shape_hotest);
        } else if (label.equals(Newest)) {
            return context.getResources().getDrawable(R.drawable.shape_newest);
        } else if (label.equals(Top)) {
            return context.getResources().getDrawable(R.drawable.shape_top);
        } else {
            return null;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mDrawable != null) {
            setVisibility(VISIBLE);
            setText(label);
            setTextColor(Color.WHITE);
            setGravity(Gravity.CENTER);
            setBackground(mDrawable);
        } else {
            setVisibility(GONE);
        }
    }

    public void setLabel(String label) {
        this.label = label;
        mDrawable = getDrawable(mContext, label);
        invalidate();
    }
}
