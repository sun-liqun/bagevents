package com.bagevent.new_home.new_activity;

import android.content.Context;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;

/**
 * =============================================
 * <p>
 * author sunun
 * <p>
 * copy ©2016 百格活动
 * <p>
 * time 2019/01/10
 * <p>
 * desp
 * <p>
 * <p>
 * =============================================
 */
public abstract class AnimatorPopupWindow extends PopupWindow {
    private OnDismissAnimatorListener onDismissAnimatorListener;

    @Override

    public void showAtLocation(View parent, int gravity, int x, int y) {
        super.showAtLocation(parent, gravity, x, y);
        postAnimateIn(parent);
    }

    private void postAnimateIn(View postView) {
//        postView.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                animateIn();
//            }
//
//        }, 1);

    }

    protected abstract void animateIn();


    public void superDismiss() {
        super.dismiss();
        if (onDismissAnimatorListener != null) {
            onDismissAnimatorListener.onDismiss();
        }
    }

    @Override

    public void dismiss() {
        animateOut();
        if (onDismissAnimatorListener != null) {
            onDismissAnimatorListener.onStartDismiss();
        }
    }

    public void onAnimationEnd() {
        superDismiss();
    }

    /**

     * PopupWindow从屏幕消失动画。在动画执行结束时请调用 {@link AnimatorPopupWindow#onAnimationEnd()}

     */
    protected abstract void animateOut();

    public void setOnDismissAnimatorListener(OnDismissAnimatorListener onDismissAnimatorListener) {
        this.onDismissAnimatorListener = onDismissAnimatorListener;
    }

    public interface OnDismissAnimatorListener {
        public void onStartDismiss();
        public void onDismiss();
    }
}
