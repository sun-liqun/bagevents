package com.bagevent.util;

import android.animation.ObjectAnimator;
import android.view.View;

/**
 * Created by zwj on 2016/9/2.
 */
public class AnimationUtils {

    public static void enterAnimation (View view,float current) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationY", 0f, current);
        animator.setDuration(6000);
        animator.start();
    }

    public static void exitAnimation (View view,float current) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationY", current, 0f);
        animator.setDuration(6000);
        animator.start();
    }
}
