package com.bagevent.new_home.new_activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bagevent.R;
import com.bagevent.new_home.HomePage;

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
public class CommentPopupWindow extends AnimatorPopupWindow {
    private PopupWindow mPopUpWindow;
    private View mPopUpView;
    private int popupWidth;
    private int popupHeight;
    private TextView tv_like;
    private TextView tv_comment_dynamic;
    private int location[];
    private Activity activity;


    public CommentPopupWindow(Context context, int width, int height) {
        super();
        View view = LayoutInflater.from(context).inflate(R.layout.activity_new_comment_pop, null);
        mPopUpView=view.findViewById(R.id.pop_content);
        setContentView(mPopUpView);
        tv_like=view.findViewById(R.id.tv_like);
        tv_comment_dynamic=view.findViewById(R.id.tv_comment_dynamic);
        mPopUpWindow = new PopupWindow(mPopUpView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        mPopUpView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        popupWidth = mPopUpView.getMeasuredWidth();    //  获取测量后的宽度
        popupHeight = mPopUpView.getMeasuredHeight();  //获取测量后的高度
        location = new int[2];
        mPopUpWindow.setTouchable(true);
        mPopUpWindow.setOutsideTouchable(true);
        mPopUpWindow.setFocusable(true);
        mPopUpWindow.setOutsideTouchable(true);
//        mPopUpWindow.setAnimationStyle(R.style.PopWindowAnimation);
//        mPopUpView.getLocationOnScreen(location);
        mPopUpWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

    }

    protected void animateIn() {
        ValueAnimator animator = createDropAnimator(mPopUpView, 0,popupWidth);
        animator.start();
        mPopUpView.setVisibility(View.VISIBLE);
    }

    @Override
    protected void animateOut() {
        ValueAnimator animator = createDropAnimator(mPopUpView, popupWidth, 0);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mPopUpView.setVisibility(View.GONE);
            }
        });
        animator.start();
    }

    private ValueAnimator createDropAnimator(final View view, int start, int end) {
        ValueAnimator animator=ValueAnimator.ofInt(start,end);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int value= (int) valueAnimator.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams=view.getLayoutParams();
                layoutParams.width=value;
                layoutParams.height=popupHeight;
//                view.setLayoutParams(layoutParams);
            }
        });
        return animator;
    }
}
