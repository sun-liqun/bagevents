package com.bagevent.view.mydialog;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.widget.TextView;

import com.bagevent.R;

import razerdp.basepopup.BasePopupWindow;

/**
 * Created by WenJie on 2017/11/9.
 */

public class CommonPopupWindow extends BasePopupWindow implements View.OnClickListener {

    private TextView tvFreeSend;
    private TextView tvScenePay;
    private ConfirmListener confirmListener;

    public CommonPopupWindow(Context context) {
        super(context);
        tvFreeSend = (TextView)findViewById(R.id.tv_free_send);
        tvScenePay = (TextView)findViewById(R.id.tv_scene_pay);
        setViewClickListener(this,tvFreeSend,tvScenePay);
    }

    @Override
    protected Animation initShowAnimation() {
        AnimationSet set=new AnimationSet(false);
        Animation shakeAnima=new RotateAnimation(0,15,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        shakeAnima.setDuration(400);
        set.addAnimation(getDefaultAlphaAnimation());
        set.addAnimation(shakeAnima);
        return null;
    }

    @Override
    public View getClickToDismissView() {
        return getPopupWindowView();
    }

    @Override
    public View onCreatePopupView() {
        return createPopupById(R.layout.popup_layout_ticket);
    }

    @Override
    public View initAnimaView() {
        return findViewById(R.id.ll_popupwindow);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_free_send:
                confirmListener.freeSend();
                break;
            case R.id.tv_scene_pay:
                confirmListener.scenePay();
                break;
        }
    }

    public void setOnConfirmListener(ConfirmListener listener) {
        this.confirmListener = listener;
    }

    public interface ConfirmListener {
        void freeSend();
        void scenePay();
    }
}
