package com.bagevent.view;

import android.content.Context;
import android.util.AttributeSet;

import com.zhy.autolayout.AutoLinearLayout;

/**
 * =============================================
 * <p>
 * author lshsh
 * <p>
 * copy ©2016 百格活动
 * <p>
 * time 2018/12/4
 * <p>
 * desp
 * <p>
 * <p>
 * =============================================
 */
public class MyLinearLayout extends AutoLinearLayout {
    private OnResizeListener mOnResizeListener;

    public MyLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (mOnResizeListener != null) {
            mOnResizeListener.onResize(w, h, oldw, oldh);
        }
    }

    public void setOnResizeListener(OnResizeListener listener) {
        this.mOnResizeListener = listener;
    }

    public interface OnResizeListener {
        void onResize(int w, int h, int oldw, int oldh);
    }
}
