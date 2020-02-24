package com.bagevent.home.anim_util;

import android.animation.TypeEvaluator;

/**
 * Created by zwj on 2016/9/6.
 */
public class AnimEvaluator implements TypeEvaluator {
    @Override
    public Object evaluate(float fraction, Object startValue, Object endValue) {
        AnimPoint startPoint = (AnimPoint)startValue;
        AnimPoint endPoint = (AnimPoint)endValue;
        float x = startPoint.getX() + fraction * (endPoint.getX() - startPoint.getX());
        float y = startPoint.getY() + fraction * (endPoint.getY() - startPoint.getY());
    //    AnimPoint point = new AnimPoint(x,y);
        return new AnimPoint(x,y);
    }
}
