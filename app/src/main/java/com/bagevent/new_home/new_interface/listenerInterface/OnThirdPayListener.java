package com.bagevent.new_home.new_interface.listenerInterface;

import com.bagevent.new_home.data.WXPayData;

/**
 * Created by zwj on 2017/10/23.
 */

public interface OnThirdPayListener {
    void showAlipaySuccess(String orderStr);
    void showAliPayFailed(String errInfo);
    void showWxpaySuccess(WXPayData response);
    void showThirdPayFailed(String errInfo);
}
