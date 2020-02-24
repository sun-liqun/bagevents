package com.bagevent.new_home.new_interface.new_view;

import android.content.Context;

import com.bagevent.new_home.data.WXPayData;

/**
 * Created by zwj on 2017/10/23.
 */

public interface ThirdPayView {
    Context mContext();
    String userId();
    String orderNum();
    String payway();

    void showAliPaySuccess(String orderStr);
    void showAliPayFailed(String errInfo);
    void showThirdPaySuccess(WXPayData response);
    void showThirdPayFailed(String errInfo);

}

