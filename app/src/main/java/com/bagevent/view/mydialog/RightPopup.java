package com.bagevent.view.mydialog;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.CycleInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;

import com.bagevent.R;
import com.zhy.autolayout.AutoLinearLayout;

import razerdp.basepopup.BasePopupWindow;

/**
 * Created by WenJie on 2017/11/2.
 */

public class RightPopup extends BasePopupWindow implements View.OnClickListener{

    private AutoLinearLayout llOrder;
    private AutoLinearLayout llQuitTicket;
    private AutoLinearLayout llRemark;
    public RightPopup(Context context) {
        super(context, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        llOrder = (AutoLinearLayout)findViewById(R.id.ll_order);
        llQuitTicket = (AutoLinearLayout)findViewById(R.id.ll_quit_ticket);
        llRemark = (AutoLinearLayout)findViewById(R.id.ll_remark);

        setViewClickListener(this,llOrder,llQuitTicket,llRemark);
    }

    @Override
    protected Animation initShowAnimation() {
        AnimationSet set = new AnimationSet(true);
        set.setInterpolator(new DecelerateInterpolator());
        set.addAnimation(getScaleAnimation(0, 1, 0, 1, Animation.RELATIVE_TO_SELF, 1, Animation.RELATIVE_TO_SELF, 0));
        set.addAnimation(getDefaultAlphaAnimation());
        return set;
    }

    @Override
    public View getClickToDismissView() {
        return null;
    }

    @Override
    public View onCreatePopupView() {
        return createPopupById(R.layout.pop_menu_item);
    }

    @Override
    public View initAnimaView() {
        return null;
    }


    @Override
    public void showPopupWindow(View v) {
        setOffsetX(-(getWidth() - v.getWidth() / 2));
        setOffsetY(-v.getHeight() / 3);
        super.showPopupWindow(v);
    }

    @Override
    public void onClick(View v) {

    }
}
