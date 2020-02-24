package com.bagevent.new_home.new_interface.presenter;

import com.bagevent.new_home.data.WXPayData;
import com.bagevent.new_home.new_interface.ThirdPayInterface;
import com.bagevent.new_home.new_interface.impl.ThirdPayImpl;
import com.bagevent.new_home.new_interface.listenerInterface.OnThirdPayListener;
import com.bagevent.new_home.new_interface.new_view.ThirdPayView;

/**
 * Created by zwj on 2017/10/23.
 */

public class ThirdPayPresenter {
    private ThirdPayInterface thirdPay;
    private ThirdPayView thirdPayView;

    public ThirdPayPresenter(ThirdPayView thirdPayView) {
        this.thirdPay = new ThirdPayImpl();
        this.thirdPayView = thirdPayView;
    }

    public void getThirdPay() {
        thirdPay.getThirdPay(thirdPayView.mContext(),thirdPayView.userId() ,thirdPayView.orderNum(), thirdPayView.payway(), new OnThirdPayListener() {
            @Override
            public void showAlipaySuccess(String orderStr) {
                thirdPayView.showAliPaySuccess(orderStr);
            }

            @Override
            public void showAliPayFailed(String errInfo) {
                thirdPayView.showAliPayFailed(errInfo);
            }

            @Override
            public void showWxpaySuccess(WXPayData response) {
                thirdPayView.showThirdPaySuccess(response);
            }

            @Override
            public void showThirdPayFailed(String errInfo) {
                thirdPayView.showThirdPayFailed(errInfo);
            }
        });
    }
}
