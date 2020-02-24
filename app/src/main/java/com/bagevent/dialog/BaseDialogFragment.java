package com.bagevent.dialog;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

/**
 * =============================================
 * <p>
 * author lshsh
 * <p>
 * copy ©2016 百格活动
 * <p>
 * time 2018/12/7
 * <p>
 * desp
 * <p>
 * <p>
 * =============================================
 */
public abstract class BaseDialogFragment extends DialogFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        bindView(view);
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Dialog dialog = getDialog();
        Window window = dialog.getWindow();
        WindowManager.LayoutParams attributes = window.getAttributes();
        if(getDimAmount() >= 0) {
            attributes.dimAmount = getDimAmount();
        }

        window.setBackgroundDrawableResource(android.R.color.transparent);
        attributes.gravity = getGravity();
        attributes.height = getHeight();
        attributes.width = getWidth();
        window.setAttributes(attributes);
        if (getDialogAnimationRes() > 0) {
            window.setWindowAnimations(getDialogAnimationRes());
        }
        dialog.setCanceledOnTouchOutside(isCancelableOutside());
    }

    protected int getGravity() {
        return Gravity.CENTER;
    }

    protected boolean isCancelableOutside() {
        return true;
    }

    protected int getHeight() {
        return WindowManager.LayoutParams.WRAP_CONTENT;
    }

    protected int getWidth() {
        return WindowManager.LayoutParams.WRAP_CONTENT;
    }

    /**
     * 动画效果
     *
     * @return
     */
    protected int getDialogAnimationRes() {
        return -1;
    }

    /**
     * 获取对话框背景阴影效果
     *
     * @return 0为透明 1为全黑
     */
    protected float getDimAmount() {
        return -1;
    }

    /**
     * 对话框显示的布局
     *
     * @return
     */
    protected abstract int getLayoutId();

    /**
     * 绑定视图数据
     * @param view
     */
    protected abstract void bindView(View view);
}
